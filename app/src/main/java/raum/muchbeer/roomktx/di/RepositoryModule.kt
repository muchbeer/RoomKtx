package raum.muchbeer.roomktx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import raum.muchbeer.roomktx.db.StudentDAO
import raum.muchbeer.roomktx.repository.StudentRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(dao : StudentDAO) : StudentRepository {
        return StudentRepository(dao)
    }

    //Hint make sure the dao function is also created as Module in DatabaseModule or NetworkModule
}