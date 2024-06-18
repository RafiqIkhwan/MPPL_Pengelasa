package com.example.rpl_pengelasan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rpl_pengelasan.data.entity.Anggota
import com.example.rpl_pengelasan.data.entity.Customer

@Dao
interface CustomerDao{
    @Insert
    suspend fun insertAll(vararg customer: Customer)

    @Delete
    suspend fun delete(customer: Customer)

    @Query("SELECT * FROM customer")
    suspend fun getAll(): List<Customer>

    @Query("SELECT * FROM customer WHERE id=:idCustomer")
    suspend fun getOne(idCustomer: Int): List<Customer>

    @Update
    suspend fun updateCustomer(vararg customer: Customer)
}