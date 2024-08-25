package com.example.mytasks.repository

import com.example.mytasks.database.model.TaskModel

interface DatabaseDataSource {

    suspend fun getAll(): List<TaskModel>?

    suspend fun getById(id: Long): TaskModel

    suspend fun insert(task: TaskModel)

    suspend fun update(task: TaskModel)

    suspend fun delete(id: Long)

    suspend fun deleteAll()
}