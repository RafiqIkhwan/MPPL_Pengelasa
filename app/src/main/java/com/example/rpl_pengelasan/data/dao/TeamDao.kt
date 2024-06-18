package com.example.rpl_pengelasan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rpl_pengelasan.data.entity.Anggota
import com.example.rpl_pengelasan.data.entity.Team

@Dao
interface TeamDao{
    @Insert
    suspend fun insertAll(vararg team: Team)

    @Delete
    suspend fun delete(team: Team)

    @Query("SELECT * FROM team")
    suspend fun getAll(): List<Anggota>

//    @Query("SELECT * FROM anggota WHERE id=:idTeam")
//    suspend fun getOne(idTeam: Int): List<Team>

    @Update
    suspend fun updateTeam(vararg team: Team)
}