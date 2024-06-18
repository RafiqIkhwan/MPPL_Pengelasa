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
import com.example.rpl_pengelasan.data.entity.User
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TambahAnggota : AppCompatActivity() {

    val db by lazy { RoomDB(this) }

    var anggota_id_edit: Int = 0;
    var intentType: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_anggota)

        val toolbar: Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Mengaktifkan tombol kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }

        anggota_id_edit = intent.getIntExtra("bawa_id", 0);
        intentType = intent.getIntExtra("intentType", 0);

        val inputName: EditText = findViewById(R.id.input_name);
        val inputPhone: EditText = findViewById(R.id.input_phone);
        val btnSave : MaterialButton = findViewById(R.id.btn_save);

        if (intentType == 1){
            // ketika mode edit
            CoroutineScope(Dispatchers.IO).launch {
                val anggotaOne = db.anggotaDao().getOne(
                    anggota_id_edit
                )
                withContext(Dispatchers.Main){
                    // Toast.makeText(applicationContext, "Anggota berhasil ditambahkan", Toast.LENGTH_LONG).show()
                    // kembali ke activity sebelumnya
                    // finish()
                    inputName.setText(anggotaOne.first().name)
                    inputPhone.setText(anggotaOne.first().phone_number)
                    btnSave.setText("Update data")
                }
            }
        }

        btnSave.setOnClickListener{
            if (inputName.text.isNotEmpty() && inputPhone.text.isNotEmpty() ){
                if (intentType == 0) {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.anggotaDao().insertAll(
                            Anggota(
                                null,
                                inputName.text.toString(),
                                "+62${inputPhone.text.toString()}"
                            )
                        )
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                applicationContext,
                                "Anggota berhasil ditambahkan",
                                Toast.LENGTH_LONG
                            ).show()
                            // kembali ke activity sebelumnya
                            finish()
                        }
                    }
                }else if (intentType == 1){
                    CoroutineScope(Dispatchers.IO).launch {
                        db.anggotaDao().updateAnggota(
                            Anggota(
                                anggota_id_edit,
                                inputName.text.toString(),
                                "+62${inputPhone.text.toString()}"

                            )
                        )
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                applicationContext,
                                "Anggota berhasil diedit",
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
            // startActivity(Intent(this, HalamanKedua::class.java))
            onBackPressedDispatcher.onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}