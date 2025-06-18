package com.classnumber_00_domaekazuki.st42_kadai04

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id :Int = 0,
    var title: String,
    var description : String,
)
