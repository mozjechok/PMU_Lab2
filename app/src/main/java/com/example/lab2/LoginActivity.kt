package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<Button>(R.id.LoginBtn)
        loginBtn.setOnClickListener {
            findViewById<TextView>(R.id.LoginErrorText).text = ""

            val login = findViewById<EditText>(R.id.LoginEdit).text.toString()
            val password = findViewById<EditText>(R.id.PasswordEdit).text.toString()

            if(login == "max" && password == "max")
            {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                var user: User = User()
                user.login = login
                user.password = password
                intent.putExtra(User::class.java.simpleName, user)
                startActivity(intent)
            }

        }
    }
}