package com.example.latihandatabinding.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.latihandatabinding.R
import com.example.latihandatabinding.databinding.FragmentStudentDetailBinding
import com.example.latihandatabinding.model.Student
import com.example.latihandatabinding.util.loadImage
import com.example.latihandatabinding.viewmodel.DetailViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_student_detail.*
import java.util.concurrent.TimeUnit

class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentStudentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val studentId = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(studentId)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.studentLiveData.observe(viewLifecycleOwner) {
            dataBinding.student = it
            dataBinding.createNotificationListener = object: CreateNotificationClickListener {
                override fun onCreateNotificationClick(view: View, student: Student) {
                    Observable.timer(5, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("Messages: ", "five seconds")
                            MainActivity.showNotification(student.name.toString(),
                                "A new notification created",
                                R.drawable.ic_baseline_person_24)
                        }
                }
            }

            dataBinding.updateButtonListener = object: UpdateButtonClickListener {
                override fun onUpdateButtonClick(view: View, student: Student) {
                    Toast.makeText(view.context, "Data Updated", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).popBackStack()
                }
            }
        }
    }

}