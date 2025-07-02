package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

// @Entity = このクラスをデータベースのテーブルにする魔法の印
@Entity
data class Memo(
    // @PrimaryKey = このフィールドが「ID」として使われる
    // autoGenerate = true は「自動で番号を振ってもらう」という意味
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,    // 1, 2, 3... と自動で番号が振られる

    // 実際のメモの内容を保存するフィールド
    val text: String,    // ユーザーが入力したテキストがここに入る

    // 現在時刻を登録
    val createdAt: Long = System.currentTimeMillis()
)