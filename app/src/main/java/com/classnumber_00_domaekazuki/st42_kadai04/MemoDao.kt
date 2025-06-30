package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.* // Roomの機能を全部使える

@Dao
interface MemoDao {
    // suspendを付けると非同期でDBとやり取りできる
    // データの取得
    @Query("SELECT * FROM memo")
    suspend fun getAll(): List<Memo> // 結果はMemoリストで返却される
    // データの登録
    @Insert
    suspend fun insert(memo: Memo) // Memoオブジェクトを受け取って保存
    // データの削除
    @Delete
    suspend fun delete(memo: Memo) // 削除したいMemoオブジェクトを受け取る
}