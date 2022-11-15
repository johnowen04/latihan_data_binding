package com.example.latihandatabinding.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.latihandatabinding.R
import com.example.latihandatabinding.model.Student
import com.example.latihandatabinding.util.loadImage
import kotlinx.android.synthetic.main.student_list_item.view.*

class StudentListAdapter(val studentList: ArrayList<Student>) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {
    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]

        with(holder.view) {
            txtID.text = student.id
            txtStudentName.text = student.name

            btnDetail.setOnClickListener {
                val action = StudentListFragmentDirections.actionStudentDetailFragment(student.id.toString())
                Navigation.findNavController(it).navigate(action)
            }

            imgStudent.loadImage(student.photoUrl, progressBarImage)
        }
    }

    override fun getItemCount(): Int = studentList.size

    fun updateStudentList(newStudentList: ArrayList<Student>) {
        studentList.clear()
        studentList.addAll(newStudentList)
        notifyDataSetChanged()
    }
}