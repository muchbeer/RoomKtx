package raum.muchbeer.roomktx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@AndroidEntryPoint
class HiltActivity : AppCompatActivity() {

    //this is called field injection
    @Inject
   lateinit var fielInjectMrMachibya: MrMachibya

   //how to inject function using Interface
   @Inject
   lateinit var constructorFamily : Family

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt)

        println("Familia ya John Machibya")
        println(fielInjectMrMachibya.mtotoWaMachibya())
        println(fielInjectMrMachibya.mjukuu())

        println("---------")
        println("Constructor Injection with Interface")
        println("Familia ya Machibya")
        println(constructorFamily.callMachibyaFamily())

    }
}

class MrMachibya

@Inject
constructor(private val mjukuuWaMachibya:MrGeorge) {
    fun mtotoWaMachibya() : String {
        return "Mtoto wa tatu ni George"
    }

    fun mjukuu() : String {
        return mjukuuWaMachibya.mtotoWaGeorge()
    }
}

class MrGeorge
@Inject
constructor() {
    fun mtotoWaGeorge() : String {
        return "Mtoto wa George ni Gadiel"
    }
}

class Family
@Inject
constructor( private val machibyaFamilyImpl: Machibya) {
    fun callMachibyaFamily() : String {
       return machibyaFamilyImpl.watotoWanne()
    }
}

class MachibyaFamilyImpl
@Inject
constructor(private @Named("wife_key") val wife : String) : Machibya {
    override fun watotoWanne(): String {
       return "His wife is ${wife} and the Watoto wa Machibya ni Jose, John, George, Evelyn"
    }

}
interface Machibya {
    fun watotoWanne() : String
}

//Using provides makes it very very easy
//to implement MachibyaFamilyImpl
//so let us do it




