package raum.muchbeer.roomktx.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class Student(
    
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    var student_id : Int,
    @ColumnInfo(name = "student_name")
    var student_name : String,
    @ColumnInfo(name = "student_course")
    var student_course : String){

}
