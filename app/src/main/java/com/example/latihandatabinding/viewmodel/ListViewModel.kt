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

class ListViewModel(application: Application): AndroidViewModel(application) {

    val studentsLiveData = MutableLiveData<ArrayList<Student>>()
    val studentLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()

    val TAG = "volleyTAG"
    private var queue: RequestQueue? = null

    fun refresh() {
        /*
        studentsLiveData.value = arrayListOf(
            Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100" +
                    ".jpg/cc0000/ffffff"),
            Student("13312","Rich","1994/12/14","3925444073","http://dummyimage.com/75x100" +
                    ".jpg/5fa2dd/ffffff"),
            Student("11204","Dinny","1994/10/07","6827808747","http://dummyimage.com/75x100.jpg/5fa2dd/ffffff1")
        )
        */

        studentLoadErrorLiveData.value = false
        loadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://adv.jitusolution.com/student.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Student>>() { }.type
                val result = Gson().fromJson<ArrayList<Student>>(it, sType)
                studentsLiveData.value = result
                loadingLiveData.value = false
                Log.d("showvolley", it)
            },
            {
                Log.e("showvolley", it.toString())
                studentLoadErrorLiveData.value = true
                loadingLiveData.value = false
            }
        ).apply { tag = TAG }

        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}