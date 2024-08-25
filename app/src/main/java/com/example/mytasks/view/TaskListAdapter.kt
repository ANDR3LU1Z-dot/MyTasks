package com.example.mytasks.view

import android.R
import android.content.res.Resources
import android.graphics.Paint
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.database.model.TaskModel
import com.example.mytasks.databinding.TaskItemLayoutBinding
import com.example.mytasks.viewmodel.TasksViewModel


class TaskListAdapter(private val taskList: List<TaskModel>, private val viewModel: TasksViewModel) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    companion object {
        const val TAG = "TaskListAdapter"
    }

    private var values: List<TaskModel> = ArrayList()

    var onItemClick: ((task: TaskModel) -> Unit)? = null

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

    override fun getItemCount(): Int  = taskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = taskList[position]
        holder.bindItem(item)
    }

    inner class ViewHolder(binding: TaskItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {

            val view: View = binding.root
            private val taskTitle = binding.title

            private val checkBox = binding.checkBoxList

            fun bindItem(item: TaskModel){
                taskTitle.text = item.title
                checkBox.isChecked = item.done

                view.setOnClickListener {
                    onItemClick?.invoke(item)
                }

                checkBox.setOnClickListener {
                    item.done = checkBox.isChecked
                    viewModel.updateTask(item)
                }

                if(checkBox.isChecked){
                    val spanBuilder = SpannableStringBuilder(taskTitle.text)
                    val strikethroughSpan = StrikethroughSpan()
                    spanBuilder.setSpan(strikethroughSpan, 0, taskTitle.text.length, 0)
                    taskTitle.text = spanBuilder
                    taskTitle.paintFlags = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    taskTitle.setTextColor(ResourcesCompat.getColor(Resources.getSystem(), R.color.darker_gray, null))
                }

            }
    }

    private fun changeCheckBox(checkBox: CheckBox, done: Boolean){
        when(done){
            true -> checkBox.isChecked = true
            else -> checkBox.isChecked = false
        }

    }
}