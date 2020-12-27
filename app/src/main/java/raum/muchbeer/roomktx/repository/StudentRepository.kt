package raum.muchbeer.roomktx.repository

import raum.muchbeer.roomktx.db.StudentDAO
import raum.muchbeer.roomktx.model.Student

class StudentRepository(private val dao: StudentDAO) {

    val retrieve =  dao.retrieveStudentData()

    suspend fun insertStudent(student : Student) {  dao.insertStudent(student)   }

    suspend fun updateStuddentStudent(student : Student) {   dao.updateStudent(student)    }

    suspend fun deleteStudent(student : Student) {   dao.deleteStudent(student)     }

    suspend fun deleteAllStudent() {
        dao.deleteAll()
    }
}