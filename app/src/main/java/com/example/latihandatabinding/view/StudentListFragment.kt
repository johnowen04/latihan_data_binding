package com.example.latihandatabinding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihandatabinding.R
import com.example.latihandatabinding.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_student_list.*

class StudentListFragment : ListFragment() {
    private lateinit var viewModel: ListViewModel
    private var adapter = StudentListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = adapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            txtError.visibility = View.GONE
            progressLoad.visibility = View.VISIBLE

            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.studentsLiveData.observe(viewLifecycleOwner) {
            adapter.updateStudentList(it)
        }

        viewModel.studentLoadErrorLiveData.observe(viewLifecycleOwner) {
            txtError.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            progressLoad.visibility = if (it) View.VISIBLE else View.GONE
            recView.visibility = if (it) View.GONE else View.VISIBLE
        }
    }
}