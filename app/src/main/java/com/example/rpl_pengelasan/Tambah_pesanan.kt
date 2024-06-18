package com.example.rpl_pengelasan

import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ListView
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.geometry.Rect
import com.example.rpl_pengelasan.data.RoomDB
import com.example.rpl_pengelasan.data.entity.Anggota
import com.example.rpl_pengelasan.data.entity.Customer
import com.example.rpl_pengelasan.data.entity.Pesanan
import com.example.rpl_pengelasan.data.entity.Team
import com.example.rpl_pengelasan.data_model.AdapterListAnggota
import com.example.rpl_pengelasan.data_model.ModelListAnggota
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class Tambah_pesanan : AppCompatActivity(), AdapterView.OnItemClickListener{

    val db by lazy { RoomDB(this) }
    lateinit var pelangganMap : Map<String?, Int?>
    lateinit var listAnggota : ArrayList<Anggota>
    val anggotaTerpilih = mutableListOf<Anggota>()

    private lateinit var listView: ListView
    private lateinit var adapterX: ArrayAdapter<String>
    private var dataModel: ArrayList<ModelListAnggota>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pesanan)

        val spinnerPelanggan = findViewById<Spinner>(R.id.spinner_pelanggan)
        val inputCatatan : EditText = findViewById(R.id.input_catatan)
        val scrollView = findViewById<ScrollView>(R.id.Scroll_view)

        // Tambahkan listener untuk mengamati perubahan visibilitas keyboard
        val rootView = window.decorView.rootView
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height

            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) { // 0.15 adalah rasio tinggi keyboard terhadap layar
                // Keyboard muncul
                scrollView.post {
                    val y = inputCatatan.y.toInt() - scrollView.height / 2
                    scrollView.scrollTo(0, y)
                }
            } else {
                // Keyboard hilang
                scrollView.scrollTo(0, 0)
            }
        }

        // mengaktifkan fungsi back toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Mengaktifkan tombol kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btn_save : MaterialButton = findViewById(R.id.btn_save)



        var anggotaTerpilih = ArrayList<Anggota>();



        btn_save.setOnClickListener{
            if (spinnerPelanggan.selectedItemPosition == AdapterView.INVALID_POSITION){
                Toast.makeText(this, "silakan pilih pelanggan yang memesan pesanan ini.", Toast.LENGTH_LONG).show()
            }else{
                val pelangganTerpilih = spinnerPelanggan.selectedItem.toString();
                val listView: ListView = findViewById(R.id.multiple_pilih);

                if (pelangganMap.isNotEmpty()){

                    var customerId = pelangganMap[pelangganTerpilih]
//                    Toast.makeText(this, pelangganTerpilih, Toast.LENGTH_LONG).show()

                    var datePicker :DatePicker = findViewById(R.id.datePicker)
                    var datePicker2 :DatePicker = findViewById(R.id.datePicker2)
                    var tanggal = Calendar.getInstance();
                    var tanggal2 = Calendar.getInstance();
                    tanggal.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                    tanggal2.set(datePicker2.year, datePicker2.month, datePicker2.dayOfMonth)

                    var catatan : String = findViewById<EditText?>(R.id.input_catatan).text.toString()

                    // mulai menginput data pesanan
                    if (customerId != null){
                        if (!catatan.isEmpty()){


                        CoroutineScope(Dispatchers.IO).launch {
                            val cek = db.pesananDao().getAll();
                            println(cek.size)
                            withContext(Dispatchers.Main) {
                                if (cek.size != 0 ){

//                                    Toast.makeText(this@Tambah_pesanan, pelangganTerpilih, Toast.LENGTH_LONG).show()
                                    var idnya = cek.last().id;
                                    if ( idnya != null) {
                                        idnya++;
                                    }else {
                                        idnya = 1;
                                    }
                                    CoroutineScope(Dispatchers.IO).launch {
                                        db.pesananDao().insertAll(
                                            Pesanan(
                                                idnya,
                                                tanggal.time,
                                                tanggal2.time,
                                                customerId,
                                                catatan,
                                                ""
                                            )
                                        )
//                                            var peserta = dataModel?.filter { it.checked }
//                                            if (peserta != null) {
//                                                for (pe in peserta) {
//                                                    db.teamDao().insertAll(
//                                                        Team(
//                                                            null,
//                                                            idnya,
//                                                            pe.id
//                                                        )
//                                                    )
//                                                }
//                                            }
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Pesanan berhasil ditambahkan "+customerId+" "+pelangganTerpilih,
                                                Toast.LENGTH_LONG
                                            ).show()
                                            // kembali ke activity sebelumnya
                                            finish()
                                        }
                                    }
                                }else{
//                                    Toast.makeText(this@Tambah_pesanan, "jangkrik bos", Toast.LENGTH_LONG).show()

                                    CoroutineScope(Dispatchers.IO).launch {
                                        db.pesananDao().insertAll(
                                            Pesanan(
                                                1,
                                                tanggal.time,
                                                tanggal2.time,
                                                customerId,
                                                catatan,
                                                ""
                                            )
                                        )

                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Pesanan berhasil ditambahkan"+customerId+" "+pelangganTerpilih,
                                                Toast.LENGTH_LONG
                                            ).show()
                                            // kembali ke activity sebelumnya
                                            finish()
                                        }
                                    }
                                }
                            }
                        }
                    }
                        else{
                            Toast.makeText(this, "silakan lengkapi data yang diperlukan .", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else{
                    Toast.makeText(this, "silakan pilih pelanggan yang memesan pesanan ini.", Toast.LENGTH_SHORT).show()
                }
                }
            }

        listView = findViewById(R.id.multiple_pilih);
//

    }

    override fun onStart() {
        super.onStart()
        var listAdapter: ArrayAdapter<String>
        var pelanggan : List<Customer> = ArrayList()
        var pelanggan_nama : List<String?>

        CoroutineScope(Dispatchers.IO).launch {
            pelanggan = db.customerDao().getAll()

            listAnggota = db.anggotaDao().getAll() as ArrayList<Anggota>
            // tampilkan ke userAdapter
            var nama = ArrayList<String>()
            dataModel = ArrayList()
            for (anggota in listAnggota){
                if (anggota.name != null && anggota.id != null){
                    nama.add(anggota.name)
                    dataModel!!.add(ModelListAnggota(anggota.name, false, anggota.id));
                    println(anggota.name)
                }
            }
            // adapter untuk listview checkbox
//            adapterX = AdapterListAnggota(dataModel!!, applicationContext);
            //
            adapterX = ArrayAdapter(
                this@Tambah_pesanan,
                android.R.layout.simple_list_item_multiple_choice,
                nama
            )

            withContext(Dispatchers.Main){
                // spinnerr
                pelanggan_nama = pelanggan.map { it.name }
                pelangganMap = pelanggan.associateBy( { it.name }, { it.id },)
                val adapter = ArrayAdapter(this@Tambah_pesanan, android.R.layout.simple_spinner_item, pelanggan_nama)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                val spinnerPelanggan = findViewById<Spinner>(R.id.spinner_pelanggan)
                spinnerPelanggan.adapter = adapter

                // listView
                listView = findViewById(R.id.multiple_pilih);
                listView.adapter = adapterX;
//                listView.choiceMode = AbsListView.CHOICE_MODE_MULTIPLE;

//                listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//                    val dataModel: ModelListAnggota = dataModel!![position] as ModelListAnggota
//                    dataModel.checked = !dataModel.checked
//                    println(dataModel.name)
////                    adapterX.notifyDataSetChanged()
//                    println(dataModel.checked)
//                }
                listView.onItemClickListener = this@Tambah_pesanan
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var items: String = parent?.getItemAtPosition(position) as String
        val dataModel: ModelListAnggota = dataModel!![position] as ModelListAnggota
        dataModel.checked = !dataModel.checked
        println(dataModel.name);
        println(dataModel.checked);
//        println(items)
    }



}