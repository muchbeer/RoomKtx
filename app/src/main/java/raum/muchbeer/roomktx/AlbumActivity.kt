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
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import raum.muchbeer.roomktx.databinding.ActivityAlbumBinding
import raum.muchbeer.roomktx.model.Album
import raum.muchbeer.roomktx.model.AlbumItem
import raum.muchbeer.roomktx.retrofit.AlbumService
import raum.muchbeer.roomktx.retrofit.RetrofitInstance
import retrofit2.Response

class AlbumActivity : AppCompatActivity() {

    private lateinit var getRetrofitService : AlbumService
    private lateinit var binding : ActivityAlbumBinding
    private val channelID = "raum.muchbeer.roomktx.channelID"
    private var notificationManager:NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_album)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album)

     getRetrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "GeorgeName", "Wake Kotlin")

        binding.btnNotification.setOnClickListener{
            displayNotification()
        }
    //    getAllAlbum()
      //  getPathAlbum()
        getResponseSearch()
        uploadAlbum()

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

        val notification = NotificationCompat.Builder(this@AlbumActivity, channelID)
            .setContentTitle("MachibyaTitle")
            .setContentText("This is the best family we have ")
            .setSmallIcon(android.R.drawable.ic_media_next)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(action)
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