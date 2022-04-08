package com.sattoholic.todayquote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var idx: Int? = null,
    @ColumnInfo
    var text: String,
    @ColumnInfo
    var from: String = ""
)