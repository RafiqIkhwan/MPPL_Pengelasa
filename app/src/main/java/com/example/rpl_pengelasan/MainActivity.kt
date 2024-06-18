package com.example.rpl_pengelasan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rpl_pengelasan.data.RoomDB
import com.example.rpl_pengelasan.data.entity.User
import com.example.rpl_pengelasan.ui.theme.RPL_pengelasanTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    val db by lazy { RoomDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RPL_pengelasanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        setContentView(R.layout.activity_login)

        val editTextLogin : EditText = findViewById(R.id.pin_masuk);
        val btn_login : Button = findViewById(R.id.btn_login);
        val tanya_pin : TextView = findViewById(R.id.tanya_pin)
        val link_register : TextView = findViewById(R.id.link_register);
        val link_lupa_pin : TextView = findViewById(R.id.lupa_pin);

        var user : List<User>
        CoroutineScope(Dispatchers.IO).launch {
            user = db.userDao().getAll()
            // tampilkan ke userAdapter
            if (user.isEmpty()){
                tanya_pin.visibility = View.VISIBLE
                link_register.visibility = View.VISIBLE

            }else{
                tanya_pin.visibility = View.GONE
                link_register.visibility = View.GONE
            }
        }


        btn_login.setOnClickListener {
            val pinValue = editTextLogin.text.toString()
            if (pinValue.isEmpty()) {
                Toast.makeText(applicationContext, "Silakan masukan pin anda", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {

                    val user: List<User> = db.userDao().getAll()

                    withContext(Dispatchers.Main) {
                        if (user.isEmpty()) {
                            Toast.makeText(applicationContext, "Maaf pin aplikasi belum dibuat Silakan register dahulu", Toast.LENGTH_LONG).show()
                        } else {
                            if (pinValue == user.firstOrNull()?.pin) {
                                startActivity(Intent(this@MainActivity, HalamanKedua::class.java))
                            } else {
                                // Tampilkan pesan error atau lakukan tindakan lain
                                Toast.makeText(applicationContext, "Pin aplikasi yang anda masukan tidak sesuai", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        link_register.setOnClickListener {
            startActivity(
                Intent(this, Register::class.java)
            )
        }
        link_lupa_pin.setOnClickListener{
            startActivity(
                Intent(this, Lihat_User::class.java)
            )
        }

    }




}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RPL_pengelasanTheme {
        Greeting("Android")
    }
}