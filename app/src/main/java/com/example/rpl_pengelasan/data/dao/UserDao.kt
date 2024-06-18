package com.example.rpl_pengelasan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rpl_pengelasan.data.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Update
    suspend fun updateUsers(vararg users: User)
}