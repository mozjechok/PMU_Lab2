package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
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
        if (arguments != null)
            user =  arguments.getSerializable(User::class.java.simpleName) as User

        val loginEdit = findViewById<EditText>(R.id.MainLoginEdit)
        loginEdit.setText(user.login)

        val fioEdit = findViewById<EditText>(R.id.MainFIOEdit)
        fioEdit.setText(user.FIO)

        val unikEdit = findViewById<EditText>(R.id.MainUnikEdit)
        unikEdit.setText(user.Unik)

        val dateEdit = findViewById<EditText>(R.id.MainDateEdit)
        dateEdit.setText(user.BirthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))

        val gradeEdit = findViewById<EditText>(R.id.MainGradeEdit)
        gradeEdit.setText(user.Grade)

        val editBtn = findViewById<Button>(R.id.EditBtn)
        editBtn.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            intent.putExtra(User::class.java.simpleName, user)
            startActivity(intent)
        }
    }
}