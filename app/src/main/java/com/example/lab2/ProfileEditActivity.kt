package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


class ProfileEditActivity : AppCompatActivity() {
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        val arguments = intent.extras

        if (arguments != null) {
            user =  arguments.getSerializable(User::class.java.simpleName) as User
            val login = user.login
            val password = user.password

            val response = httpPost {
                url("http://192.168.0.112:8000/api/v1/getUser")

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
                val intent = Intent(this@ProfileEditActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            } else {
                var gson = Gson()
                user = gson.fromJson(obj["result"].toString(), User::class.java)
            }
        }

        val loginEdit = findViewById<EditText>(R.id.EditLoginEdit)
        loginEdit.setText(user.login)

        val fioEdit = findViewById<EditText>(R.id.EditFIOEdit)
        fioEdit.setText(user.fio)

        val unikEdit = findViewById<EditText>(R.id.EditUnikEdit)
        unikEdit.setText(user.unik)

        val dateEdit = findViewById<EditText>(R.id.EditDateEdit)
        dateEdit.setText(user.birthdate)
        dateEdit.addTextChangedListener(object : TextWatcher {


            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                val df = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val saveBtn = findViewById<Button>(R.id.SaveBtn)
                var testDate: LocalDate?
                try {
                    testDate = LocalDate.parse(s.toString(), df)
                    if(testDate > LocalDate.now()) {
                        saveBtn.isEnabled = false
                        dateEdit.error = "Hello from future"
                    }
                    else {
                        saveBtn.isEnabled = true
                    }
                } catch (e: DateTimeParseException) {
                    dateEdit.error = "the date you provided is in an invalid date" +
                            " format."
                    saveBtn.isEnabled = false
                }
            }
        })

        val gradeEdit = findViewById<EditText>(R.id.EditGradeEdit)
        gradeEdit.setText(user.grade)

        val saveBtn = findViewById<Button>(R.id.SaveBtn)
        saveBtn.setOnClickListener {
            val df = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            user.login     = findViewById<EditText>(R.id.EditLoginEdit).text.toString()
            user.fio       = findViewById<EditText>(R.id.EditFIOEdit).text.toString()
            user.unik      = findViewById<EditText>(R.id.EditUnikEdit).text.toString()
            user.birthdate = findViewById<EditText>(R.id.EditDateEdit).text.toString()
            user.grade     = findViewById<EditText>(R.id.EditGradeEdit).text.toString()





            val response = httpPost {
                url("http://192.168.0.112:8000/api/v1/updateUser")

                body {
                    json {
                        "login" to user.login
                        "password" to user.password
                        "fio" to user.fio
                        "unik" to user.unik
                        "grade" to user.grade
                        "birthdate" to user.birthdate
                    }
                }

                header {
                    "Content-Type" to "application/json"
                }
            }

            val obj = JSONObject(response.asString())
            if (obj["status"].toString() == "false") {
                val intent = Intent(this@ProfileEditActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.putExtra(User::class.java.simpleName, user)
                startActivity(intent)
            }
        }
    }
}