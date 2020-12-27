package raum.muchbeer.roomktx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import raum.muchbeer.roomktx.repository.StudentRepository
import java.lang.IllegalArgumentException

class StudentViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("No view model capture")
    }
}