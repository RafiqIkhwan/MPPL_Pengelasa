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
import com.example.rpl_pengelasan.adapter.UserAdapter
import com.example.rpl_pengelasan.data.RoomDB
import com.example.rpl_pengelasan.data.entity.Anggota
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LihatAnggota : AppCompatActivity() {

    val db by lazy { RoomDB(this) }
    lateinit var anggotaAdapter : AnggotaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_anggota)

        val toolbar: Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Mengaktifkan tombol kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }

        val btn_tambah :MaterialButton = findViewById(R.id.btn_tambah)
        btn_tambah.setOnClickListener{
            startActivity(
                Intent(applicationContext, TambahAnggota::class.java)
            )
        }

        val list_anggota : RecyclerView = findViewById(R.id.show_anggota)

        // untuk menampilkan data
        anggotaAdapter = AnggotaAdapter(
            arrayListOf(),
            object : AnggotaAdapter.OnAdapterListener{
                override fun onClick(anggota: Anggota) {
                    Toast.makeText(applicationContext, anggota.name, Toast.LENGTH_SHORT).show();
                    // karena kita perlu melakuakn pengko
//                    startActivity(
//                        Intent(applicationContext, TambahAnggota::class.java)
//                            .putExtra("bawa_id", anggota.id)
//                    )
                }
                override fun onClick(anggota: Anggota, intentType: Int){
                    when(intentType){
                        1 -> {
                            // edit
                            Toast.makeText(applicationContext, anggota.name+" Edit", Toast.LENGTH_SHORT).show();
                            startActivity(
                                Intent(applicationContext, TambahAnggota::class.java)
                                    .putExtra("bawa_id", anggota.id)
                                    .putExtra("intentType", intentType)
                            )
                        } 2 -> {
                            // Delete
                            CoroutineScope(Dispatchers.IO).launch {
                                db.anggotaDao().delete(anggota)
                                val anggotaReload = db.anggotaDao().getAll()
                                // tampilkan ke userAdapter
                                withContext(Dispatchers.Main){
                                    Toast.makeText(applicationContext, anggota.name+" Delete", Toast.LENGTH_SHORT).show();
                                    anggotaAdapter.setData(anggotaReload)
                                }
                            }
                        }

                    }
                }
            }
        )
        list_anggota.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = anggotaAdapter
        }
    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            // Kembali ke halaman sebelumnya
            onBackPressedDispatcher.onBackPressed()
            // startActivity(Intent(this, HalamanKedua::class.java))
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val anggota = db.anggotaDao().getAll()
            // tampilkan ke userAdapter
            withContext(Dispatchers.Main){
                anggotaAdapter.setData(anggota)
            }
        }
    }
}