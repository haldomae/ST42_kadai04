package com.classnumber_00_domaekazuki.st42_kadai04

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// @Databaseを付けると、このクラスがデータベースの全体である事を示している
@Database(
    // このデータベースで使うテーブル(エンティティ)を指定
    entities = [Memo::class],
    // データベースのバージョン(変更時に増やす)
    version = 1
)
// RoomDatabaseを継承する事によって、Roomの機能を使えるようにする
abstract class AppDatabase: RoomDatabase() {
    // DAOの橋渡し(MemoDaoを使いたいときにはここを実行する)
    abstract fun memoDao(): MemoDao
}

// データベースを実際に作成する関数
fun getDatabase(context: Context): AppDatabase{
    // Room.databaseBuilderはDBを組み立てる工場
    return Room.databaseBuilder(
        context, // アプリの情報
        AppDatabase::class.java, // 作りたいDBのクラス
        "memo-db" // データベースのファイル名
    ).build()
}