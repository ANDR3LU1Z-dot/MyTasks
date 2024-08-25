package com.example.mytasks.repository

import android.content.Context
import com.example.mytasks.database.AppDataBase
import com.example.mytasks.database.daos.TaskDao
import com.example.mytasks.database.model.TaskModel

class TaskRepository(context: Context): DatabaseDataSource {

     private val appDatabase = AppDataBase.getDatabase(context)
     private val taskDao = appDatabase.taskDao(appDatabase)

     override suspend fun getAll(): List<TaskModel>? = taskDao.getAll()

     override suspend fun getById(id: Long): TaskModel = taskDao.getById(id)

     override suspend fun insert(task: TaskModel) = taskDao.insert(task)

     override suspend fun update(task: TaskModel) = taskDao.update(task)

     override suspend fun delete(id: Long) = taskDao.delete(id)

     override suspend fun deleteAll() = taskDao.deleteAll()

 }