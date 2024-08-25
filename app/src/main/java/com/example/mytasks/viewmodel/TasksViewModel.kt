package com.example.mytasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytasks.database.model.TaskModel
import com.example.mytasks.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public class TasksViewModel(private val application: Application): AndroidViewModel(application) {

    val taskList: LiveData<List<TaskModel>>
        get() = _taskList

    private val _taskList = MutableLiveData<List<TaskModel>>()

    val task: LiveData<TaskModel>
        get() = _task

    private val _task = MutableLiveData<TaskModel>()

    private val repository = TaskRepository(application)

    init {
        getTaskList()
    }

    fun getTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            _taskList.postValue(repository.getAll())
        }
    }

    fun insertOrUpdateTask(task: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (task.id == 0L) {
                insertTask(task)
            } else {
                updateTask(task)
            }
        }
    }

    fun insertTask(task: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(task)
            getTaskList()
        }
    }

    fun updateTask(task: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(task)
            getTaskList()
        }
    }

    fun deleteTask(task: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(task.id)
            getTaskList()
        }
    }

    fun getTaskById(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            _task.postValue(repository.getById(id))
        }

    }

}
