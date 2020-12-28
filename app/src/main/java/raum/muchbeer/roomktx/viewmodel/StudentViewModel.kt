package raum.muchbeer.roomktx.viewmodel

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import raum.muchbeer.roomktx.model.Student
import raum.muchbeer.roomktx.repository.StudentRepository
import raum.muchbeer.roomktx.util.Event
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException


//to use bindable text we need to implement Observable
class StudentViewModel(private val repository: StudentRepository) : ViewModel(), Observable {

    val retrieveStudent = repository.retrieve


    private var isUpdateorDelete = false
    private lateinit var isStudentUpdateOrDelete : Student
    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputCourse = MutableLiveData<String>()

    @Bindable
    val btnSaveUpdate = MutableLiveData<String>()

    @Bindable
    val btnClearAllDelete = MutableLiveData<String>()

    init {
        btnSaveUpdate.value = "Save"
        btnClearAllDelete.value = "Clear All"
    }


    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
    get() = statusMessage

    fun saveOrUpdate() {
        //if you want to validate email
       // if(Patterns.EMAIL_ADDRESS.matcher(inputName.value!!).matches())
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter name of student")
            inputCourse.value = null
        } else if (inputCourse.value ==null) {
            statusMessage.value = Event("Please enter course of student")
            inputName.value = null
        } else {
            if(isUpdateorDelete) {
                isStudentUpdateOrDelete.student_name = inputName.value!!
                isStudentUpdateOrDelete.student_course = inputCourse.value!!

                updateStudent(isStudentUpdateOrDelete)
            } else {
                val studentName = inputName.value!!
                val courseName = inputCourse.value!!
                if (studentName ==null) {
                    statusMessage.value = Event("Please enter name of student")
                } else if(courseName == null) {
                    statusMessage.value = Event("Please enter course of student")
                } else {
                    insertStudent(Student(0, studentName, courseName))
                    inputName.value = null
                    inputCourse.value = null
                }

            }
        }


    }

    fun clearAllOrDelete() {

        if(isUpdateorDelete) {
            deleteStudent(isStudentUpdateOrDelete)
        } else {
            clearAll()
        }

    }
    fun insertStudent(student: Student)  = viewModelScope.launch {
         val newRowId =   repository.insertStudent(student)
        if(newRowId > -1) {
            statusMessage.value = Event("Student added Successful at ${newRowId}")
        } else {
            statusMessage.value = Event("Student was not added Successful")
        }
        }
    fun updateStudent(student: Student) = viewModelScope.launch {
       val updateRowId =  repository.updateStuddentStudent(student)
        if(updateRowId > 0) {
            inputName.value = null
            inputCourse.value = null
            isUpdateorDelete = false
            btnSaveUpdate.value = "Save"
            btnClearAllDelete.value = "Clear"
            statusMessage.value = Event("Student updated Successful")
        } else {
            statusMessage.value = Event("No record were found")
        }}

        fun deleteStudent(student: Student) = viewModelScope.launch {
            repository.deleteStudent(student)

            inputName.value = null
            inputCourse.value = null
            isUpdateorDelete = false
            btnSaveUpdate.value = "Save"
            btnClearAllDelete.value = "Clear"
            statusMessage.value = Event("Student deleted Successful")
        }

    fun clearAll() =viewModelScope.launch {
        repository.deleteAllStudent()
        statusMessage.value = Event("Student Cleared Successful")
    }

    fun initUpdateDelete(student: Student) {
        inputName.value = student.student_name
        inputCourse.value = student.student_course
        isUpdateorDelete = true
        isStudentUpdateOrDelete = student
        btnSaveUpdate.value = "Update"
        btnClearAllDelete.value = "Delete"


    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}



