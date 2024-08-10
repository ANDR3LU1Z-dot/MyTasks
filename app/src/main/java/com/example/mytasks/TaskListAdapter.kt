package com.example.mytasks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.databinding.TaskItemLayoutBinding


interface TaskItemListener {
    fun onItemSelected(position: Int)
}

class TaskListAdapter(private val listener: TaskItemListener) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    companion object {
        const val TAG = "TaskListAdapter"
    }

    private var values: List<TaskModel> = ArrayList()

    fun updateData(taskList: List<TaskModel>){
        values = taskList
        Log.d(TAG, "values from task list adapter: $values")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TaskItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int  = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bindItem(item)
        holder.view.setOnClickListener {
            listener.onItemSelected(position)
        }
    }

    inner class ViewHolder(private val binding: TaskItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {

            val view: View = binding.root
            private val taskTitle = binding.title

            private val checkBox = binding.checkBoxList

            fun bindItem(item: TaskModel){
                taskTitle.text = item.titleTask
                changeCheckBox(checkBox, item.done)
            }
    }

    private fun changeCheckBox(checkBox: CheckBox, done: Boolean){
        when(done){
            true -> checkBox.isChecked = true
            else -> checkBox.isChecked = false
        }

    }
}