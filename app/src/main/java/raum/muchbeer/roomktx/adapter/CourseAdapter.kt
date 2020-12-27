package raum.muchbeer.roomktx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.roomktx.R
import raum.muchbeer.roomktx.databinding.StudentListItemBinding
import raum.muchbeer.roomktx.model.Student

class CourseAdapter(private val studentList : List<Student>,
              private val selectedItem: (Student)->Unit) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): CourseAdapter.CourseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : StudentListItemBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.student_list_item, parent, false)

        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseAdapter.CourseViewHolder, position: Int) {
        holder.bind(studentList[position], selectedItem)
    }

    override fun getItemCount(): Int {
       return studentList.size
    }

    class CourseViewHolder(val itemViewBinding: StudentListItemBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(student : Student, selectedItem: (Student)->Unit) {
            itemViewBinding.txtName.text = student.student_name
            itemViewBinding.txtCourse.text = student.student_course
            itemViewBinding.layoutClick.setOnClickListener {
                selectedItem(student)
            }
        }

    }
}