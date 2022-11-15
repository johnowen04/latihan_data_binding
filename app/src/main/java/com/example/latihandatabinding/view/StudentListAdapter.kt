package com.example.latihandatabinding.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.latihandatabinding.R
import com.example.latihandatabinding.databinding.StudentListItemBinding
import com.example.latihandatabinding.model.Student
import com.example.latihandatabinding.util.loadImage
import kotlinx.android.synthetic.main.student_list_item.view.*

class StudentListAdapter(val studentList: ArrayList<Student>) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {
    class StudentViewHolder(var view: StudentListItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = StudentListItemBinding.inflate(inflater, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        with(holder.view) {
            student = studentList[position]
            studentDetail = object: StudentDetailClickListener {
                override fun onStudentDetailClick(view: View) {
                    val action = StudentListFragmentDirections
                        .actionStudentDetailFragment(view.tag.toString())
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int = studentList.size

    fun updateStudentList(newStudentList: ArrayList<Student>) {
        studentList.clear()
        studentList.addAll(newStudentList)
        notifyDataSetChanged()
    }
}