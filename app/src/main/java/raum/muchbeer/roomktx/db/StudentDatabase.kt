package raum.muchbeer.roomktx.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import raum.muchbeer.roomktx.model.Student

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao() : StudentDAO
    //in Kotlin we create singleton as companion object

    companion object {
        @Volatile
      private  var INSTANCE : StudentDatabase?= null

        fun getStudentInstance(context : Context) : StudentDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance ==null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "student_database"
                    ).build()
                }
                return instance
            }

        }


    }
}