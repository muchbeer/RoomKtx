package raum.muchbeer.roomktx.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import raum.muchbeer.roomktx.domain.DomainMapper
import raum.muchbeer.roomktx.retrofit.AlbumService
import raum.muchbeer.roomktx.retrofit.RetrofitInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

/*    @Singleton
    @Provides
    fun provideStudentMap() : DomainMapper {
        return DomainMapper()
    }*/

    @Singleton
    @Provides
    fun provideStudentService() : AlbumService {
        return  Retrofit.Builder()
            .baseUrl(RetrofitInstance.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(AlbumService::class.java)
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String{
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }
}