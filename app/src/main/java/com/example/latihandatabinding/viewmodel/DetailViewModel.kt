package com.example.latihandatabinding.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.latihandatabinding.model.Student
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailViewModel(application: Application): AndroidViewModel(application) {

    val studentLiveData = MutableLiveData<Student>()

    private lateinit var queue: RequestQueue

    fun fetch(studentId: String) {
        /*
        studentLiveData.value = Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100" +
                ".jpg/cc0000/ffffff")
         */

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubaya.fun/hybrid/160420016/mvvm_api/getstudent.php?id=$studentId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<Student>() { }.type
                val result = Gson().fromJson<Student>(it, sType)
                studentLiveData.value = result
                Log.d("showvolley", it)
            },
            {
                Log.e("showvolley", it.toString())
            }
        )

        queue.add(stringRequest)
    }
}