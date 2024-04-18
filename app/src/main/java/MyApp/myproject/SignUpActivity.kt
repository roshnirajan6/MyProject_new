package MyApp.myproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myproject.R
import MyApp.myproject.database.MyDB
import MyApp.myproject.database.MyEntity
import android.widget.ImageButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
//initializing views
        val nameEditText = findViewById<EditText>(R.id.editTextName)
        val emailEditText = findViewById<EditText>(R.id.editTextEmailSignUp)
        val passwordEditText = findViewById<EditText>(R.id.editTextPasswordSignUp)
        val signupButton = findViewById<Button>(R.id.buttonSignUp)
        val loginTextView = findViewById<TextView>(R.id.loginTextView)
        val backButton = findViewById<ImageButton>(R.id.imageButtonBackButtonSignUp)
        //creating db
        val db = Room.databaseBuilder(this, MyDB::class.java, "mydatabase")
            .fallbackToDestructiveMigration()
            .build()
        var h = Handler()//handler
        backButton.setOnClickListener{//listener for back button
           finish()
        }
        loginTextView.setOnClickListener {//listener for login
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
        signupButton.setOnClickListener {//listener for signup button
            val myName = nameEditText.text.toString()
            val myEmail = emailEditText.text.toString()
            val myPassword = passwordEditText.text.toString()
            if(myName.isEmpty() || myEmail.isEmpty() || myPassword.isEmpty()){//check for empty fields
                Toast.makeText(this,"Please fill the fields before signup", Toast.LENGTH_LONG).show()
            }
            GlobalScope.launch {//coroutine to save data
                val existingUser = db.myDao().getUserByEmail(myEmail)
                if (existingUser == null) {
                    val newUser = MyEntity().apply {
                        Name = myName
                        Email = myEmail
                        Password = myPassword
                    }
                    db.myDao().saveData(newUser)
                    h.post {//in main thread clear data in the view and move to main activity
                        nameEditText.setText("")
                        emailEditText.setText("")
                        passwordEditText.setText("")
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        intent.putExtra("keyEmail",myEmail)
                        startActivity(intent)
                    }
                } else {
                    h.post {//if user already exists, give toast
                        Toast.makeText(this@SignUpActivity,"Email ID already exists, Sign up with a new email ID", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}