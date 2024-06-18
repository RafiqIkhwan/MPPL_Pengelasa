package com.example.rpl_pengelasan.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.lang.reflect.Array
import java.util.Date


@Entity(
    tableName = "pesanan",
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = ["id"],
            childColumns = ["pelangganId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Pesanan (
    @PrimaryKey val id: Int? = 1,
    @ColumnInfo(name = "tgl_pesanan") val tgl_pesanan : Date,
    @ColumnInfo(name = "tgl_deadline") val tgl_deadline : Date,
    @ColumnInfo(name = "pelangganId") val pelangganId: Int,
    @ColumnInfo(name = "isi_catatan") val isi_catatan: String?,
    @ColumnInfo(name = "pathImage") val pathImage: String?

)