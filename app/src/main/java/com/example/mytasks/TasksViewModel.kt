package com.example.mytasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TasksViewModel(): ViewModel() {

    val taskList: LiveData<List<TaskModel>>
        get() = _taskList

    val _taskList = MutableLiveData<List<TaskModel>>()


    fun updadeTaskList(task: TaskModel) {

    }
}