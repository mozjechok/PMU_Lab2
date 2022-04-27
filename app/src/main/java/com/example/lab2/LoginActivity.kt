package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerBtn = findViewById<Button>(R.id.RegisterBtn)
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.LoginBtn)
        loginBtn.setOnClickListener {
            findViewById<TextView>(R.id.LoginErrorText).text = ""

            lifecycleScope.launch {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                val login = findViewById<EditText>(R.id.LoginEdit).text.toString()
                val password = findViewById<EditText>(R.id.PasswordEdit).text.toString()

                val response = httpPost {
                    url(url + "getUser")

                    body {
                        json {
                            "login" to login
                            "password" to password
                        }
                    }

                    header {
                        "Content-Type" to "application/json"
                    }
                }

                val obj = JSONObject(response.asString())
                if (obj["result"].toString() == "" || obj["result"].toString()  == "[]") {
                    findViewById<TextView>(R.id.LoginErrorText).text = "неверный логин/пароль"
                } else {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                    var user: User = User()
                    user.login = login
                    user.password = password
                    intent.putExtra(User::class.java.simpleName, user)
                    startActivity(intent)
                }

                /*if(login == "admin" && password == "admin")
                {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                    var user: User = User()
                    user.login = login
                    user.password = password
                    intent.putExtra(User::class.java.simpleName, user)
                    startActivity(intent)
                }*/
            }
        }
    }
}