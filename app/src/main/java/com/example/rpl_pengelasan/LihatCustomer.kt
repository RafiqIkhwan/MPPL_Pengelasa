package com.example.rpl_pengelasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rpl_pengelasan.adapter.AnggotaAdapter
import com.example.rpl_pengelasan.adapter.CustomerAdapter
import com.example.rpl_pengelasan.data.RoomDB
import com.example.rpl_pengelasan.data.entity.Customer
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LihatCustomer : AppCompatActivity() {

    val db by lazy { RoomDB(this) }
    lateinit var customerAdapter : CustomerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_customer)

        val toolbar: Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Mengaktifkan tombol kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }

        val list_customer : RecyclerView = findViewById(R.id.show_pelanggan)

        val btn_tambah : MaterialButton = findViewById(R.id.btn_tambah)

        btn_tambah.setOnClickListener{
            startActivity(
                Intent(applicationContext, TambahCustomer::class.java)
            )
        }

        // untuk menampilkan data
        customerAdapter = CustomerAdapter(
            arrayListOf(),
            object : CustomerAdapter.OnAdapterListener{
                override fun onClick(customer: Customer) {
                    TODO("Not yet implemented")
                }

                override fun onClick(customer: Customer, intentType: Int) {
                    if (intentType == 1){
                        // update
                        startActivity(
                            Intent(applicationContext, TambahCustomer::class.java)
                                .putExtra("bawa_id", customer.id)
                                .putExtra("intentType", intentType)
                        )
                    }else if (intentType == 2){
                        // delete
                        CoroutineScope(Dispatchers.IO).launch {
                            db.customerDao().delete(customer)
                            val customerReload = db.customerDao().getAll()
                            // tampilkan ke userAdapter
                            withContext(Dispatchers.Main){
                                Toast.makeText(applicationContext, customer.name+" Delete", Toast.LENGTH_SHORT).show();
                                customerAdapter.setData(customerReload)
                            }
                        }
                    }
                }
            }
        )
        list_customer.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = customerAdapter
        }

    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            // Kembali ke halaman sebelumnya
            onBackPressedDispatcher.onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val customer = db.customerDao().getAll()
            // tampilkan ke userAdapter
            withContext(Dispatchers.Main){
                customerAdapter.setData(customer)
            }
        }
    }

}