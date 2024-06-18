package com.example.rpl_pengelasan

import android.content.Intent
import android.content.ReceiverCallNotAllowedException
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rpl_pengelasan.adapter.PesananAdapter
import com.example.rpl_pengelasan.data.RoomDB
import com.example.rpl_pengelasan.data.entity.Customer
import com.example.rpl_pengelasan.data.entity.Pesanan
import com.example.rpl_pengelasan.databinding.ActivityHalamanKeduaBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HalamanKedua : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHalamanKeduaBinding
    lateinit var pesananAdapter: PesananAdapter

    val db by lazy { RoomDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHalamanKeduaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val btn_tambahPesanan : MaterialButton = findViewById(R.id.btn_tambahPesanan)
        btn_tambahPesanan.setOnClickListener{

            startActivity(
                Intent(applicationContext, Tambah_pesanan::class.java)
            )
        }

        val recyclerView : RecyclerView = findViewById(R.id.show_pesanan)

        pesananAdapter = PesananAdapter(
            arrayListOf(),
            object: PesananAdapter.OnAdapterListener{
                override fun onClick(pesanan: Pesanan) {
                    TODO("Not yet implemented")
                }

                override fun onClick(pesanan: Pesanan, intentType: Int) {
                    if (intentType == 1){
                        // update
//                        CoroutineScope(Dispatchers.IO).launch {
//                            db.pesananDao().getOne()
//                            val anggotaReload = db.anggotaDao().getAll()
//                            // tampilkan ke userAdapter
//                            withContext(Dispatchers.Main){
//                                Toast.makeText(applicationContext, anggota.name+" Delete", Toast.LENGTH_SHORT).show();
//                                anggotaAdapter.setData(anggotaReload)
//                            }
//                        }
                        println("page2 pelanggan id  "+pesanan.pelangganId)
                        println("page2 pesanan id "+pesanan.id)
                        println("page2 tglpesan "+pesanan.tgl_pesanan)
                        println("page2 tglDL "+pesanan.tgl_deadline)
                        println("page2 Catatan "+pesanan.isi_catatan)

                        startActivity(
                            Intent(applicationContext, LihatSatu_Pesanan::class.java)
                                .putExtra("id_pesanan", pesanan.id)
                                .putExtra("tgl_pesanan", pesanan.tgl_pesanan)
                                .putExtra("tgl_deadline", pesanan.tgl_deadline)
                                .putExtra("id_customer", pesanan.pelangganId)
                                .putExtra("catatan", pesanan.isi_catatan)
                                .putExtra("intentType", intentType)
                        )
                    }
                }
            },
            this
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = pesananAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val pesanan = db.pesananDao().getAll()
            // tampilkan ke userAdapter
            withContext(Dispatchers.Main){
                pesananAdapter.setData(pesanan)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.pengaturan -> {
                Toast.makeText(applicationContext, "Pengaturan", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.add_anggota -> {
                startActivity(Intent(this, TambahAnggota::class.java))
                // Toast.makeText(applicationContext, "Add Anggota", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.add_customer -> {
                startActivity(Intent(this, TambahCustomer::class.java))
                true
            }
            R.id.show_customer -> {
                startActivity(Intent(this, LihatCustomer::class.java))
                true
            }
            R.id.show_anggota -> {
                startActivity(Intent(this, LihatAnggota::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}