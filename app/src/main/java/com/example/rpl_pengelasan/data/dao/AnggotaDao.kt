package com.example.rpl_pengelasan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rpl_pengelasan.data.entity.Anggota

@Dao
interface AnggotaDao{
    @Insert
    suspend fun insertAll(vararg anggota: Anggota)

    @Delete
    suspend fun delete(anggota: Anggota)

    @Query("SELECT * FROM anggota")
    suspend fun getAll(): List<Anggota>

    @Query("SELECT * FROM anggota WHERE id=:id_anggota")
    suspend fun getOne(id_anggota: Int): List<Anggota>

    @Update
    suspend fun updateAnggota(vararg anggota: Anggota)
}