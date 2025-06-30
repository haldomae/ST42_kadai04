package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(
    // @PrimaryKey : 主キーとして扱われる
    // autoGenerate = true : 自動で番号を振って貰う
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // 連番のキー(主キー)
    val text: String // ユーザが入力したテキストがここに入る
)
