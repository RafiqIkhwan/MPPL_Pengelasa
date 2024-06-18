package com.example.rpl_pengelasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rpl_pengelasan.adapter.UserAdapter
import com.example.rpl_pengelasan.data.RoomDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Lihat_User : AppCompatActivity() {

    val db by lazy { RoomDB(this) }
    lateinit var userAdapter : UserAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_user)


        val btn_kembali:  Button = findViewById(R.id.kembali_ke_login);
        val list_user: RecyclerView = findViewById(R.id.list_user)
        // evnt
        btn_kembali.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        // untuk menampilkan data
        userAdapter = UserAdapter(arrayListOf())
        list_user.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = userAdapter
        }

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val user = db.userDao().getAll()
            // tampilkan ke userAdapter
            withContext(Dispatchers.Main){
                userAdapter.setData(user)
            }
        }
    }


}