package raum.muchbeer.roomktx.db

import androidx.lifecycle.LiveData
import androidx.room.*
import raum.muchbeer.roomktx.model.Student

@Dao
interface StudentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student : Student) : Long

    @Update
    suspend fun updateStudent(student: Student) : Int

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("DELETE FROM students_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM students_table ORDER BY student_name ASC")
    fun retrieveStudentData() : LiveData<List<Student>>

    @Query("SELECT * FROM students_table ")
    suspend fun retrieveStudentList() : List<Student>
}