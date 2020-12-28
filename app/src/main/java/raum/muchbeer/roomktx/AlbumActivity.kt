package raum.muchbeer.roomktx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import raum.muchbeer.roomktx.model.Album
import raum.muchbeer.roomktx.retrofit.AlbumService
import raum.muchbeer.roomktx.retrofit.RetrofitInstance
import retrofit2.Response

class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        val getRetrofit = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        val albumResponse : LiveData<Response<Album>> = liveData {
            val response = getRetrofit.getAlbum()
            emit(response)
        }

        albumResponse.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList !=null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    Log.d("AlbumActivity", "The list is ${albumItem.title}")
                }
            }
        })
    }
}