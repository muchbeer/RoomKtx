package raum.muchbeer.roomktx.retrofit

import raum.muchbeer.roomktx.model.Album
import raum.muchbeer.roomktx.model.AlbumItem
import retrofit2.Response
import retrofit2.http.*

interface AlbumService {

    //we are using suspend function because we are going to use corotine
    //GET represent the end point but not the Default url point
    @GET("/albums")
    suspend fun getAlbum() : Response<Album>


    //using field query
    @GET("/albums")
    suspend fun sortAlbumId(@Query("userId") userId:Int) : Response<Album>

    //using path parameter to search the path
    @GET("/albums/{id}")
    suspend fun getPathID(@Path(value = "id") albumId:Int) : Response<AlbumItem>

    @POST("/albums")
    suspend fun uploadAlbum(@Body album : AlbumItem) : Response<AlbumItem>
}