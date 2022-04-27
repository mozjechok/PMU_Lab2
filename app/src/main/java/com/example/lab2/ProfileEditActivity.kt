package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
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
        }

        val loginEdit = findViewById<EditText>(R.id.EditLoginEdit)
        loginEdit.setText(user.login)

        val fioEdit = findViewById<EditText>(R.id.EditFIOEdit)
        fioEdit.setText(user.FIO)

        val unikEdit = findViewById<EditText>(R.id.EditUnikEdit)
        unikEdit.setText(user.Unik)

        val dateEdit = findViewById<EditText>(R.id.EditDateEdit)
        dateEdit.setText(user.BirthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
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
        gradeEdit.setText(user.Grade)

        val saveBtn = findViewById<Button>(R.id.SaveBtn)
        saveBtn.setOnClickListener {
            val df = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            user.login     = findViewById<EditText>(R.id.EditLoginEdit).text.toString()
            user.FIO       = findViewById<EditText>(R.id.EditFIOEdit).text.toString()
            user.Unik      = findViewById<EditText>(R.id.EditUnikEdit).text.toString()
            user.BirthDate = LocalDate.parse(findViewById<EditText>(R.id.EditDateEdit).text.toString(), df)
            user.Grade     = findViewById<EditText>(R.id.EditGradeEdit).text.toString()


            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(User::class.java.simpleName, user)
            startActivity(intent)
        }
    }
}