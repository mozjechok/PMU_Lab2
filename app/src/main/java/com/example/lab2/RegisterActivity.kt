package com.example.lab2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val loginBtn = findViewById<Button>(R.id.LoginBtn)
        loginBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        val registerBtn = findViewById<Button>(R.id.RegisterBtn)
        registerBtn.setOnClickListener {
            findViewById<TextView>(R.id.RegisterErrorText).text = ""

            lifecycleScope.launch {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                val login = findViewById<EditText>(R.id.LoginEdit).text.toString()
                val password = findViewById<EditText>(R.id.PasswordEdit).text.toString()
                val email = findViewById<EditText>(R.id.EmailEdit).text.toString()
                val phone = findViewById<EditText>(R.id.PhoneEdit).text.toString()

                val response = httpPost {
                    url("http://192.168.0.112:8000/api/v1/newUser")


                    body {
                        json {
                            "login" to login
                            "password" to password
                            "email" to email
                            "phone" to phone
                        }
                    }

                    header {
                        "Content-Type" to "application/json"
                    }
                }

                val obj = JSONObject(response.asString())
                if (obj["status"].toString() == "false") {
                    findViewById<TextView>(R.id.RegisterErrorText).text = "пользователь c данным логином уже существует"
                } else {
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                    var user: User = User()
                    user.login = login
                    user.password = password
                    intent.putExtra(User::class.java.simpleName, user)
                    startActivity(intent)
                }

            }
        }
    }
}