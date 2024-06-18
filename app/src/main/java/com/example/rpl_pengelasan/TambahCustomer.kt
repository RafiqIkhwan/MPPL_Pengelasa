package com.example.rpl_pengelasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.rpl_pengelasan.data.RoomDB
import com.example.rpl_pengelasan.data.entity.Anggota
import com.example.rpl_pengelasan.data.entity.Customer
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TambahCustomer : AppCompatActivity() {

    val db by lazy { RoomDB(this) }

    var id_customer_update : Int = 0
    var intentType : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_customer)

        val toolbar: Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Mengaktifkan tombol kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }

        id_customer_update = intent.getIntExtra("bawa_id", 0)
        intentType = intent.getIntExtra("intentType", 0)

        val inputName: EditText = findViewById(R.id.input_name);
        val inputAddress: EditText = findViewById(R.id.input_address);
        val inputPhone: EditText = findViewById(R.id.input_phone);
        val btnSave : MaterialButton = findViewById(R.id.btn_save);

        if (intentType == 1){
            CoroutineScope(Dispatchers.IO).launch {
                var cs = db.customerDao().getOne(
                    id_customer_update
                )
                withContext(Dispatchers.Main){
                    inputName.setText(cs.first().name)
                    inputAddress.setText(cs.first().address)
                    inputPhone.setText(cs.first().phone_number)
                    btnSave.setText("Update Data")
                }
            }
        }

        btnSave.setOnClickListener{
            if (inputName.text.isNotEmpty() && inputPhone.text.isNotEmpty() && inputAddress.text.isNotEmpty()){

                if (intentType == 0){
                    CoroutineScope(Dispatchers.IO).launch {
                        db.customerDao().insertAll(
                            Customer(
                                null,
                                inputName.text.toString(),
                                inputAddress.text.toString(),
                                "+62${inputPhone.text.toString()}"

                            )
                        )
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                applicationContext,
                                "Pelanggan berhasil ditambahkan",
                                Toast.LENGTH_LONG
                            ).show()
                            // kembali ke activity sebelumnya
                            finish()
                        }
                    }
                }else if (intentType == 1){
                    // update
                    CoroutineScope(Dispatchers.IO).launch {
                        db.customerDao().updateCustomer(
                            Customer(
                                id_customer_update,
                                inputName.text.toString(),
                                inputAddress.text.toString(),
                                "+62${inputPhone.text.toString()}"

                            )
                        )
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                applicationContext,
                                "Data pelanggan berhasil diupdate",
                                Toast.LENGTH_LONG
                            ).show()
                            // kembali ke activity sebelumnya
                            finish()
                        }
                    }
                }

            }else{
                Toast.makeText(applicationContext, "Silakan isi semua data di atas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            // Kembali ke halaman sebelumnya
            startActivity(Intent(this, HalamanKedua::class.java))
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}