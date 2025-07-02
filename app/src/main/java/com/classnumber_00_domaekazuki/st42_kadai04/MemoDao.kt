package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.*

// @Dao = このインターフェースがデータアクセスオブジェクトだと宣言
@Dao
interface MemoDao {

    // 🔍 データを「取得」する操作
    // @Query = SQLクエリ（データベース言語）を書く場所
    @Query("SELECT * FROM memo")  // 「memoテーブルから全部取ってきて」という意味
    suspend fun getAll(): List<Memo>  // 結果はMemoのリストで返される

    // IDから該当のデータを「取得」する操作を追加
    // :id の部分が引数 id の値に置き換えられる
    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun getTargetMemoData(id: Int): Memo?

    // 🔍 データを「保存」する操作
    // @Insert = 新しいデータをテーブルに追加する
    @Insert
    suspend fun insert(memo: Memo)  // Memoオブジェクトを受け取って保存

    // 🔍 データを「削除」する操作
    // @Delete = 指定されたデータをテーブルから削除する
    @Delete
    suspend fun delete(memo: Memo)  // 削除したいMemoオブジェクトを指定
}