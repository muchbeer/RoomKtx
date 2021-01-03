package raum.muchbeer.roomktx

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import raum.muchbeer.roomktx.db.StudentDAO
import raum.muchbeer.roomktx.db.StudentDatabase
import raum.muchbeer.roomktx.model.Student

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var studentDaoTest: StudentDAO
    private lateinit var database: StudentDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StudentDatabase::class.java
        ).build()

        studentDaoTest = database.studentDao()
    }
    @Test
    fun saveStudentAndRetrieveData_studentObject_SaveReadResult() = runBlocking {


        val students = listOf(
            Student(1, "George", "Comutter"),
            Student(2, "Evelyn", "Geography"),
            Student(3, "Mama", "Doctor")
        )
        students.forEach {
            studentDaoTest.insertStudent(it)
        }

        val allStudent = studentDaoTest.retrieveStudentData()
        //   studentDaoTest.insertStudent(students)
        Truth.assertThat(allStudent).isEqualTo(students)


    }



    @After
    fun tearDown() {
        database.close()
    }
}