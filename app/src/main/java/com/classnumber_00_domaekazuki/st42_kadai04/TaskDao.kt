package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    // 非同期ワンショットクエリ
    @Insert
    suspend fun insertTask(task: Task)

    // オブザーバルクエリ
    @Query("SELECT * FROM Task")
    fun loadAllTasks() : Flow<List<Task>>

    // 非同期ワンショットクエリ
    @Update
    suspend fun updateTask(task: Task)

    // 非同期ワンショットクエリ
    @Delete
    suspend fun deleteTask(task: Task)
}