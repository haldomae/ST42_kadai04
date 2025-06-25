package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity = このクラスをデータベースのテーブルにする魔法の印
@Entity
data class Memo(
    // @PrimaryKey = このフィールドが「ID」として使われる
    // autoGenerate = true は「自動で番号を振ってもらう」という意味
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,    // 1, 2, 3... と自動で番号が振られる

    // 実際のメモの内容を保存するフィールド
    val text: String    // ユーザーが入力したテキストがここに入る
)