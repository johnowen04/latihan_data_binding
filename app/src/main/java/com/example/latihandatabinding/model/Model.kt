package com.example.latihandatabinding.model

import com.google.gson.annotations.SerializedName

data class Student(
    val id: String?,
    @SerializedName("student_name")
    var name: String?,
    @SerializedName("date_of_birth")
    var dob: String?,
    var phone: String?,
    @SerializedName("photo_url")
    var photoUrl: String?
)