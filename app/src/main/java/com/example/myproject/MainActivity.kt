package com.example.myproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database instance
        val db = Room.databaseBuilder(this, MyDB::class.java, "mydatabase")
            .fallbackToDestructiveMigration()
            .build()
        var h = Handler()
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val signupButton = findViewById<Button>(R.id.buttonSignUp_Main)

        signupButton.setOnClickListener {
            val myIntent = Intent(this, SignUpActivity::class.java)
            startActivity(myIntent)
        }
        loginButton.setOnClickListener {
            val myEmail = emailEditText.text.toString()

            GlobalScope.launch {
                val existingUser = db.myDao().getUserByEmail(myEmail)
                if (existingUser != null) { // If email ID already exists
                    val existingPassword = db.myDao().getPasswordByEmail(myEmail)
                    val myPassword = passwordEditText.text.toString()

                    if (existingPassword == myPassword) { // If password matches
                        h.post {
                            Toast.makeText(
                                this@MainActivity,
                                "Login Successful",
                                Toast.LENGTH_LONG
                            ).show()
                            emailEditText.setText("")
                            passwordEditText.setText("")
                            startActivity(Intent(this@MainActivity, SplashActivity::class.java))
                        }
                    }else {
                        h.post {
                            Toast.makeText(this@MainActivity,"Password incorrect...",Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    h.post {
                        Toast.makeText(this@MainActivity, "Invalid login ID...Please Sign Up", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

