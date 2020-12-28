package raum.muchbeer.roomktx.retrofit

import raum.muchbeer.roomktx.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface AlbumService {

    //we are using suspend function because we are going to use corotine
    //GET represent the end point but not the Default url point
    @GET("/albums")
    suspend fun getAlbum() : Response<Album>
}