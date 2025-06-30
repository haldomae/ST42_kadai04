package com.classnumber_00_domaekazuki.st42_kadai04

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    // このデータベースで使うテーブル(Entity)を指定
    entities = [Memo::class],
    // バージョン番号(変更時に増やす)
    version = 1
)
// RoomDatabaseを継承する事でRoomの機能を使えるようにする
abstract class AppDatabase: RoomDatabase() {
    // DAOへの橋渡し
    abstract fun memoDao(): MemoDao
}

// データベースを実際に作成する関数
fun getDatabase(context: Context): AppDatabase{
    // データベースを組み立てる
    return Room.databaseBuilder(
        context, // アプリ情報
        AppDatabase::class.java, // 作りたいデータベースのクラス
        "memo-db" // データベースのファイル名
    ).build() // 作成開始
}