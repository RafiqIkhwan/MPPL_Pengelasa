package com.example.rpl_pengelasan.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Anggota (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "phone_number") val phone_number: String?
)