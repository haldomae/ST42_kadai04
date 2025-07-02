package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

// @Database = このクラスがデータベース全体を表すことを宣言
@Database(
    entities = [Memo::class],  // このデータベースで使うテーブル（Entity）を指定
    version = 3               // データベースのバージョン番号（変更時に増やす）
)
// RoomDatabase を継承することで、Room の機能を使える
abstract class AppDatabase : RoomDatabase() {
    // DAO への橋渡し：「MemoDao を使いたい時はここを呼んで」
    abstract fun memoDao(): MemoDao
}

// 🔍 データベースを実際に作成する関数
fun getDatabase(context: Context): AppDatabase {
    // Room.databaseBuilder = データベースを組み立てる工場
    return Room.databaseBuilder(
        context,                    // アプリの情報
        AppDatabase::class.java,    // 作りたいデータベースのクラス
        "memo-db"                   // データベースファイルの名前
    ).build()  // 「作成開始！」の合図
}