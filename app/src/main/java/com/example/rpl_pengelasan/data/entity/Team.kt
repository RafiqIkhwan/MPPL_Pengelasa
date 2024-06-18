package com.example.rpl_pengelasan.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "pesanan_id") val pesanan_id: Int,
    @ColumnInfo(name = "anggota_id") val anggota_id: Int
)