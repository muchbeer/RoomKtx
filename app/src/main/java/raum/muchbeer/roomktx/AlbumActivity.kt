package raum.muchbeer.roomktx

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.work.*
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.roomktx.databinding.ActivityAlbumBinding
import raum.muchbeer.roomktx.model.Album
import raum.muchbeer.roomktx.model.AlbumItem
import raum.muchbeer.roomktx.retrofit.AlbumService
import raum.muchbeer.roomktx.retrofit.RetrofitInstance
import raum.muchbeer.roomktx.viewmodel.TestHiltViewModel
import raum.muchbeer.roomktx.workmanager.FilterWorker
import raum.muchbeer.roomktx.workmanager.UploadWorker
import retrofit2.Response
import java.io.FilterReader
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AlbumActivity : AppCompatActivity() {

    private lateinit var getRetrofitService : AlbumService
    private lateinit var binding : ActivityAlbumBinding
    private val channelID = "raum.muchbeer.roomktx.channelID"
    private var notificationManager:NotificationManager? = null
    private val KEY_TEXT = "key_value"

    //this below is called field injection and is dope
  //  @Inject
  //  lateinit var retrieveInjection : String

    //Instatiate viewModel with Hilt
 //   val testHiltView : TestHiltViewModel by viewModels()

    //if you want to share viewModel btn different activity most important Fragment then use
  //  val testHiltView2 : TestHiltViewModel by activityViewModels()
    //Now we are good to go

    companion object {
        const val KEY_WORKER_DATA = "key_count"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_album)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album)


     getRetrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "GeorgeName", "Wake Kotlin")

        binding.btnNotification.setOnClickListener{
           // displayNotification()
            setOneTimeRequestWork()
        }
    //    getAllAlbum()
      //  getPathAlbum()
        getResponseSearch()
        uploadAlbum()

        //Inject to an activity
      //  Log.d("AlbumActivityHilt", "Inject string provide the name: ${retrieveInjection}")

    }

    private fun setOneTimeRequestWork() {

        val data : Data= Data.Builder().putInt(KEY_WORKER_DATA, 150).build()

        val workManager = WorkManager.getInstance(applicationContext)

        val constraint = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val setOnetimeRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraint)
            .setInputData(data)
            .build()

        val filterRequest = OneTimeWorkRequest.Builder(FilterWorker::class.java)
            .setConstraints(constraint)
            .setInputData(data)
            .build()

        //Joining two workers
        val parallelworker = mutableListOf<OneTimeWorkRequest>()
        parallelworker.add(setOnetimeRequest)
        parallelworker.add(filterRequest)

        //chaining more request workeer
       workManager
           .beginWith(filterRequest)
           //.beginWith(parallelworker)
           .then(setOnetimeRequest)
           .enqueue()

        workManager.getWorkInfoByIdLiveData(setOnetimeRequest.id).observe(this, Observer {
            Log.d("AlbumActivityWorkInfo", "The state name : ${it.state.name}")

            if(it.state.isFinished) {
                val receiveData = it.outputData.getString(UploadWorker.KEY_UPLOAD_DATA)
                Log.d("AlbumActivityWorkInfo", "The state name : ${receiveData}")
            }

        })
    }


    private fun setPeriodicTimeRequest() {
        val periodTime = PeriodicWorkRequest.Builder(UploadWorker::class.java, 17, TimeUnit.MINUTES)


       // WorkManager.getInstance(applicationContext).enqueue(periodTime)

    }
    private fun displayNotification() {
        val notificationID = 2
        val tapIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent:PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action : NotificationCompat.Action = NotificationCompat.Action.Builder(
            0, "Family", pendingIntent).build()

        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT).run {
            setLabel("Input your name")
            build()
        }

        val remoteReply :NotificationCompat.Action = NotificationCompat.Action.Builder(0,
        "Your name",
        pendingIntent
        ).addRemoteInput(remoteInput)
            .build()


        val notification = NotificationCompat.Builder(this@AlbumActivity, channelID)
            .setContentTitle("MachibyaTitle")
            .setContentText("This is the best family we have ")
            .setSmallIcon(android.R.drawable.ic_media_next)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        //    .setContentIntent(pendingIntent)
            .addAction(action)
            .addAction(remoteReply)
         //   .addAction(action2)
            .build()

        notificationManager?.notify(notificationID, notification)
    }

    private fun createNotificationChannel(id:String, name:String, channelDescription: String) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)

        }
    }
    private fun uploadAlbum() {
        val albums = AlbumItem(0, "Evelyn", 4)
        val responseScope : LiveData<Response<AlbumItem>> = liveData {
            val response = getRetrofitService.uploadAlbum(albums)
            emit(response)
        }

        responseScope.observe(this, Observer {
            val observePosting = it.body()
            Log.d("AlbumActivityPost", "Post value are : ${observePosting}")
        })
    }

    private fun getAllAlbum() {
        val albumResponse : LiveData<Response<Album>> = liveData {
            val response = getRetrofitService.getAlbum()
            emit(response)
        }


        albumResponse.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList !=null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    //    Log.d("AlbumActivity", "The list is ${albumItem.title}")
                }
            }
        })
    }


    private fun getResponseSearch() {
        val albumSearchUserId : LiveData<Response<Album>> = liveData {
            val response = getRetrofitService.sortAlbumId(5)
            emit(response)
        }


        //Search user ID by query the parameter
        albumSearchUserId.observe(this, Observer {
            val searchList = it.body()?.listIterator()
            if (searchList != null) {
                while (searchList.hasNext()) {
                    val searchObjects =   searchList.next()
                    Log.d("AlbumActivitySearch", "The title is : ${searchObjects.title}")
                }
            }
        })
    }

    private fun getPathAlbum() {
        val pathFindAlbum : LiveData<Response<AlbumItem>> = liveData {
            val response = getRetrofitService.getPathID(2)
            emit(response)
        }

        pathFindAlbum.observe(this, Observer {
            val titlePath = it.body()?.title
            Log.d("AlbumActivityPath", "The path 2 provided is : ${titlePath}")
        })
    }
}