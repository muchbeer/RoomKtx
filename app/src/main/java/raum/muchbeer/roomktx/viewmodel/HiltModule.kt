package raum.muchbeer.roomktx.viewmodel

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import raum.muchbeer.roomktx.Machibya
import raum.muchbeer.roomktx.MachibyaFamilyImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun providemachibyaFamilyImpl(@Named("wife_key") wifeName : String) : Machibya {
        return MachibyaFamilyImpl(wifeName)
        //we don't have a constructor in MachibyaFamilyImpl() that is why we don't instatiate
    }

    @Singleton
    @Provides
    @Named("wife_key")
    fun providemachibyaWife() : String {
        return "Clementina Kazinduki"    }
}