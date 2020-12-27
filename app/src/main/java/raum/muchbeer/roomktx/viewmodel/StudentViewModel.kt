package raum.muchbeer.roomktx.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import raum.muchbeer.roomktx.model.Student
import raum.muchbeer.roomktx.repository.StudentRepository


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

    fun saveOrUpdate() {

        if(isUpdateorDelete) {
            isStudentUpdateOrDelete.student_name = inputName.value!!
            isStudentUpdateOrDelete.student_course = inputCourse.value!!

            updateStudent(isStudentUpdateOrDelete)
        } else {
            val studentName = inputName.value!!
            val courseName = inputCourse.value!!
            insertStudent(Student(0, studentName, courseName))
            inputName.value = null
            inputCourse.value = null
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
            repository.insertStudent(student)
        }
    fun updateStudent(student: Student) = viewModelScope.launch {
        repository.updateStuddentStudent(student)

        inputName.value = null
        inputCourse.value = null
        isUpdateorDelete = false
        btnSaveUpdate.value = "Save"
        btnClearAllDelete.value = "Clear"  }

        fun deleteStudent(student: Student) = viewModelScope.launch {
            repository.deleteStudent(student)

            inputName.value = null
            inputCourse.value = null
            isUpdateorDelete = false
            btnSaveUpdate.value = "Save"
            btnClearAllDelete.value = "Clear"
        }

    fun clearAll() =viewModelScope.launch {
        repository.deleteAllStudent()
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



