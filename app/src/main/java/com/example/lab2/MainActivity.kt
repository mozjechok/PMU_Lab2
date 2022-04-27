package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import org.json.JSONObject
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Init(intent.extras)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Init(intent?.extras)

        setIntent(intent)
    }

    fun Init(arguments: Bundle?)
    {
        if (arguments != null) {
            user =  arguments.getSerializable(User::class.java.simpleName) as User
            val login = user.login
            val password = user.password

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
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            } else {
                var gson = Gson()
                user = gson.fromJson(obj["result"].toString(), User::class.java)
            }
        }

        val loginEdit = findViewById<EditText>(R.id.MainLoginEdit)
        loginEdit.setText(user.login)

        val fioEdit = findViewById<EditText>(R.id.MainFIOEdit)
        fioEdit.setText(user.fio)

        val unikEdit = findViewById<EditText>(R.id.MainUnikEdit)
        unikEdit.setText(user.unik)

        val dateEdit = findViewById<EditText>(R.id.MainDateEdit)
        dateEdit.setText(user.birthdate)

        val gradeEdit = findViewById<EditText>(R.id.MainGradeEdit)
        gradeEdit.setText(user.grade)

        val editBtn = findViewById<Button>(R.id.EditBtn)
        editBtn.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            intent.putExtra(User::class.java.simpleName, user)
            startActivity(intent)
        }
    }
}