package com.example.mytasks.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mytasks.database.model.TaskModel

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    suspend fun getAll(): List<TaskModel>?

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getById(id: Long): TaskModel

    @Insert
    suspend fun insert(task: TaskModel)

    @Update
    suspend fun update(task: TaskModel)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM task")
    suspend fun deleteAll()


}