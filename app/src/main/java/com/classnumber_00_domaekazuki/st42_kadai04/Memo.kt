package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo (
    // 連番のID
    // @PrimaryKeyを付けると主キー
    // autoGenerate = trueは自動で番号振ってくれる
    @PrimaryKey(autoGenerate = true)
    val id :Int = 0,
    // メモ本文
    val text: String
)