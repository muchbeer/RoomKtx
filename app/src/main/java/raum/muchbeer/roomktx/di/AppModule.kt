package raum.muchbeer.roomktx.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import raum.muchbeer.roomktx.BaseApplication
import javax.inject.Singleton

//this is the singleton object that in Kotlin we use Object
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : BaseApplication {
        return app as BaseApplication
    }
    @Singleton
    @Provides
    fun provideString() : String {
        return "George"
    }

}