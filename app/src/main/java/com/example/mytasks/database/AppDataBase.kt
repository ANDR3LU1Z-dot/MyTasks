package com.example.mytasks.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytasks.database.daos.TaskDao
import com.example.mytasks.database.model.TaskModel

@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun taskDao(appDataBase: AppDataBase): TaskDao

    companion object {

        @Volatile
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_data_base"
                ).build()
                this.instance = database
                return database
            }
        }
    }
}