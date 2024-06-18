package com.example.rpl_pengelasan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.rpl_pengelasan.data.RoomDB
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LihatSatu_Pesanan : AppCompatActivity() {

    val db by lazy { RoomDB(this) }

    var pesanan_id: Int = 0;
    var tgl_pesanan: Date? = null;
    var tgl_deadline: Date? = null;
    var customer_id: Int = 0;
    var catatan: String? = "";
    var intentType: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_satu_pesanan)

        pesanan_id = intent.getIntExtra("id_pesanan", 0);
        customer_id = intent.getIntExtra("id_customer", 0);

//        val tglPesananLong = intent("tgl_pesanan")
////        tgl_pesanan = if (tglPesananLong != 0L) Date(tglPesananLong) else null
//        val tglDeadlineLong = intent.getStringExtra("tgl_deadline")
////        tgl_deadline = if (tglDeadlineLong != 0L) Date(tglDeadlineLong) else null
        catatan = intent.getStringExtra("catatan");

        intentType = intent.getIntExtra("intentType", 0);

        println("pageLihat pelanggan id  "+customer_id)
        println("pageLihat pesanan id "+pesanan_id)


        println("cust id = " +customer_id)

        val namaCustomerText = findViewById<TextView>(R.id.nama_customer);
        val tgl_pesananText = findViewById<TextView>(R.id.tgl_pesanan);
        val tgl_deadlineText = findViewById<TextView>(R.id.tgl_deadline);
        val catatanText = findViewById<TextView>(R.id.catatan);
        namaCustomerText.setText("nama cust dengan id "+customer_id)
        tgl_pesananText.setText(tgl_pesanan.toString());
        tgl_deadlineText.setText(tgl_deadline.toString())
        catatanText.setText(catatan)

        if (intentType == 1){
            // ketika mode edit
            CoroutineScope(Dispatchers.IO).launch {
                val pesananOne = db.pesananDao().getOne(
                    pesanan_id
                )
                val customerOne = db.customerDao().getOne(
                    customer_id
                )
                withContext(Dispatchers.Main){
                    // Toast.makeText(applicationContext, "Anggota berhasil ditambahkan", Toast.LENGTH_LONG).show()
                    // kembali ke activity sebelumnya
                    // finish()
                    namaCustomerText.setText(customerOne.first().name)

                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    var formattedDate = dateFormat.format(pesananOne.first().tgl_pesanan)
                    tgl_pesananText.setText(formattedDate)
                    formattedDate = dateFormat.format(pesananOne.first().tgl_deadline)
                    tgl_deadlineText.setText(formattedDate)
                    catatanText.setText(pesananOne.first().isi_catatan)

                }
            }
        }

        val btn_edit = findViewById<MaterialButton>(R.id.btn_edit);
        val btn_delete = findViewById<MaterialButton>(R.id.btn_delete);



    }
}