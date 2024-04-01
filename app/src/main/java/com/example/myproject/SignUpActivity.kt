package com.example.myproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val nameEditText = findViewById<EditText>(R.id.editTextName)
        val emailEditText = findViewById<EditText>(R.id.editTextEmailSignUp)
        val passwordEditText = findViewById<EditText>(R.id.editTextPasswordSignUp)
        val signupButton = findViewById<Button>(R.id.buttonSignUp)

        val db = Room.databaseBuilder(this, MyDB::class.java, "mydatabase")
            .fallbackToDestructiveMigration()
            .build()
        var h = Handler()
        signupButton.setOnClickListener {
            val myName = nameEditText.text.toString()
            val myEmail = emailEditText.text.toString()
            val myPassword = passwordEditText.text.toString()

            GlobalScope.launch {
                val existingUser = db.myDao().getUserByEmail(myEmail)
                if (existingUser == null) {
                    val newUser = MyEntity().apply {
                        Name = myName
                        Email = myEmail
                        Password = myPassword
                    }
                    db.myDao().saveData(newUser)

                    h.post {
                        nameEditText.setText("")
                        emailEditText.setText("")
                        passwordEditText.setText("")
                        Toast.makeText(this@SignUpActivity, "Sign up successful", Toast.LENGTH_LONG).show()

                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    h.post {
                        Toast.makeText(this@SignUpActivity,"Email ID already exists. Sign up with a new email ID.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}