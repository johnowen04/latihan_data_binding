package com.example.latihandatabinding.view

import android.view.View
import com.example.latihandatabinding.model.Student

interface StudentDetailClickListener {
    fun onStudentDetailClick(view: View)
}

interface CreateNotificationClickListener {
    fun onCreateNotificationClick(view: View, student: Student)
}

interface UpdateButtonClickListener {
    fun onUpdateButtonClick(view: View, student: Student)
}