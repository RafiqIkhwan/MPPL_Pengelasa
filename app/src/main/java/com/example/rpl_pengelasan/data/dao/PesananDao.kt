package com.example.rpl_pengelasan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rpl_pengelasan.data.entity.Anggota
import com.example.rpl_pengelasan.data.entity.Pesanan

@Dao
interface PesananDao{
    @Insert
    suspend fun insertAll(vararg pesanan: Pesanan)

    @Delete
    suspend fun delete(pesanan: Pesanan)

    @Query("SELECT * FROM pesanan")
    suspend fun getAll(): List<Pesanan>

    @Query("SELECT * FROM pesanan WHERE id=:id_pesanan")
    suspend fun getOne(id_pesanan: Int): List<Pesanan>

    @Update
    suspend fun updatePesanan(vararg pesanan: Pesanan)
}