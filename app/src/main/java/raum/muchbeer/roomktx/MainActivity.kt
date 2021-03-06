package raum.muchbeer.roomktx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import raum.muchbeer.roomktx.adapter.CourseAdapter
import raum.muchbeer.roomktx.databinding.ActivityMainBinding
import raum.muchbeer.roomktx.db.StudentDatabase
import raum.muchbeer.roomktx.model.Student
import raum.muchbeer.roomktx.repository.StudentRepository
import raum.muchbeer.roomktx.viewmodel.StudentViewModel
import raum.muchbeer.roomktx.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var adapter : CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = StudentDatabase.getStudentInstance(application).studentDao()

        val repository = StudentRepository(dao)
        val factory = StudentViewModelFactory(repository)

        studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)
        binding.studentViewModel = studentViewModel
        binding.lifecycleOwner = this

        setRecyclerView()

        studentViewModel.message.observe(this, Observer {
           it.getContentIfNotHandled()?.let {
               Toast.makeText(this, it, Toast.LENGTH_LONG).show()
           }
        })
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CourseAdapter( {selectItem:Student->selectedItem(selectItem)})
        binding.recyclerView.adapter = adapter
        getStudentLiveData()
    }


    private fun getStudentLiveData() {
        studentViewModel.retrieveStudent.observe(this, Observer { listOfStudent->

            adapter.setStudentList(listOfStudent)
            adapter.notifyDataSetChanged()
      /*  listOfStudent.forEach {
            Log.d("MainActivity", "The list of student are : ${it.student_name}")
        }*/

        })
    }

    private fun selectedItem(student : Student) {
        Toast.makeText(this, "Selected student : ${student.student_name}", Toast.LENGTH_LONG).show()

        studentViewModel.initUpdateDelete(student)
    }
}