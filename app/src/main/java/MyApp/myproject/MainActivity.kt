package MyApp.myproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myproject.R
import MyApp.myproject.database.MyDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("MyInfo", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Initialize database instance
        val db = Room.databaseBuilder(this, MyDB::class.java, "mydatabase")
            .fallbackToDestructiveMigration()
            .build()
        var h = Handler()
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val signupTextView = findViewById<TextView>(R.id.textViewSignUp_Main)
        var signupEmail = intent.getStringExtra("keyEmail")
        if (signupEmail != null) {
            emailEditText.setText(signupEmail)
        }
        signupTextView.setOnClickListener {
            val myIntent = Intent(this, SignUpActivity::class.java)
            startActivity(myIntent)
        }
        loginButton.setOnClickListener {

            val myEmail = emailEditText.text.toString()
            val myPassword = passwordEditText.text.toString()

            GlobalScope.launch {
                if (myEmail.isEmpty() || myPassword.isEmpty()) {
                    h.post{

                        Toast.makeText(
                            this@MainActivity,
                            "Please fill the field before login",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }else
                {
                    val existingUser = db.myDao().getUserByEmail(myEmail)
                    if (existingUser != null) { // If email ID already exists
                        val existingPassword= existingUser.Password

                        if (existingPassword == myPassword) {
                            editor.putInt("UserId",existingUser.Id)
                            editor.putString("UserName",existingUser.Name)
                            editor.putString("UserEmail",existingUser.Email)
                            editor.apply()
                            h.post {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Login Successful ",
                                    Toast.LENGTH_LONG
                                ).show()
                                emailEditText.setText("")
                                passwordEditText.setText("")

                                var intent =  Intent(
                                        this@MainActivity,
                                        DisplayActivity::class.java
                                    )
                                startActivity(intent)

                            }
                        } else {
                            h.post {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Password incorrect...",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        h.post {
                            Toast.makeText(
                                this@MainActivity,
                                "Invalid login ID, Please Sign Up",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

