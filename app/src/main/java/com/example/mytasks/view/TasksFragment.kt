package com.example.mytasks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.R
import com.example.mytasks.databinding.FragmentTasksBinding
import com.example.mytasks.viewmodel.TasksViewModel

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: TaskListAdapter
    private lateinit var recyclerView: RecyclerView

    private val viewModel: TasksViewModel by lazy {
        ViewModelProvider(this)[TasksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.binding = FragmentTasksBinding.inflate(inflater, container, false)
//        this.adapter = TaskListAdapter()
//        this.recyclerView = binding.recyclerView
//        this.recyclerView.apply {
//            this.adapter = this@TasksFragment.adapter
//            this.layoutManager = LinearLayoutManager(context)
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        configureViewListeners()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getTaskList()
    }

    private fun initObservers() {
        viewModel.taskList.observe(viewLifecycleOwner) { taskList ->

            if (taskList.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.taskEmptyLabel.visibility = View.VISIBLE
            } else {

                val taskListAdapter = TaskListAdapter(taskList, viewModel).apply {
                    onItemClick = { task ->
                        val action =
                            TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(task)
                        findNavController().navigate(action)
                    }

                }

                binding.recyclerView.apply {
                    this.adapter = taskListAdapter
                    setHasFixedSize(true)
                    this.layoutManager = LinearLayoutManager(context)
                }

                binding.recyclerView.visibility = View.VISIBLE
                binding.taskEmptyLabel.visibility = View.GONE
            }
        }

    }

    private fun configureViewListeners(){
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_tasksFragment_to_editTaskFragment)
        }
    }
}