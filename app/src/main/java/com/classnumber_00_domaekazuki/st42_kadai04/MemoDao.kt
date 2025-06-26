package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.* // Roomの機能を全部使えるようにする

@Dao
interface MemoDao {
    // データを取得する(SELECT)
    // @QueryはSQLを記述する
    @Query("SELECT * FROM memo")
    // suspendを付けると、非同期でDBの処理が行われる
    suspend fun getAll(): List<Memo>

    // データを保存する(INSERT)
    // @InsertはSQL記述しなくてよい
    @Insert
    // メモオブジェクトを受け取って保存
    suspend fun insert(memo: Memo)

    // データを削除する(DELETE)
    // @DeleteはSQL記述しなくてもよい
    @Delete
    // メモオブジェクトを受け取って削除する
    suspend fun delete(memo: Memo)
}