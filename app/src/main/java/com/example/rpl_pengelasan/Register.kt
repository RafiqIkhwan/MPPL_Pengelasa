package com.example.rpl_pengelasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.room.CoroutinesRoom
import com.example.rpl_pengelasan.data.RoomDB
import com.example.rpl_pengelasan.data.entity.User
import com.example.rpl_pengelasan.databinding.ActivityRegisterBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Register : AppCompatActivity() {
    val db by lazy { RoomDB(this) }
//
//    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)

        val btn_register : MaterialButton = findViewById(R.id.btn_register)
        val inputName: EditText = findViewById(R.id.input_name);
        val inputEmail: EditText = findViewById(R.id.input_email);
        val inputPin: EditText = findViewById(R.id.input_pin);
        val inputPin2: EditText = findViewById(R.id.input_pin2);

        btn_register.setOnClickListener{
            // mulai proses input data
            Log.d("nama", inputName.text.toString())
            Log.d("email", inputEmail.text.toString())
            Log.d("pin", inputPin.text.toString())

            if (inputName.text.isNotEmpty() && inputEmail.text.isNotEmpty() && inputPin.text.isNotEmpty()){
                if (isEmailValid(inputEmail.text.toString())){
                    // jika email valid
                    if (inputPin.text.toString() == inputPin2.text.toString() ){
                        // jika pin sudah diulang dan valid
                        CoroutineScope(Dispatchers.IO).launch {
                            db.userDao().insertAll(
                                User(
                                    null,
                                    inputName.text.toString(),
                                    inputEmail.text.toString(),
                                    inputPin.text.toString()
                                )
                            )
                            withContext(Dispatchers.Main){
                                Toast.makeText(applicationContext, "Pin Aplikasi berhasil ditambahkan", Toast.LENGTH_LONG).show()
                                // kembali ke activity sebelumnya
                                finish()
                            }
                        }
                    }else{
                        Toast.makeText(applicationContext, "Ulangi pin anda dengan benar", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext, "Email yang anda inputkan belum valid", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext, "Silakan lengkapi data anda dengan valid", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return emailRegex.matches(email)
    }
}