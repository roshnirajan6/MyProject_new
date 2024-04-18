package MyApp.myproject
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myproject.R


class DataActivity : AppCompatActivity() {
    private lateinit var selectedItemsList: MutableList<Map<String, String>>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_data)

        val sharedPreferences = getSharedPreferences("MyInfo", MODE_PRIVATE)
        val userName = sharedPreferences.getString("UserName","null")
        val imageView = findViewById<ImageView>(R.id.imageViewMyDataImage)
        val textView = findViewById<TextView>(R.id.textViewMyDataTitle)
                //nameTextView.text=userName.toString()
        val backButton = findViewById<ImageButton>(R.id.imageButtonBackButtonData)
        var imageUrl = intent.getStringExtra("imageUrl")
        var imageTitle = intent.getStringExtra("title")

        selectedItemsList = intent.getSerializableExtra("selectedItemsList") as? ArrayList<Map<String, String>> ?: mutableListOf()
        if(imageUrl != null) {
            Glide.with(this).load(imageUrl).into(imageView)
        }
        textView.text = imageTitle

        backButton.setOnClickListener{
            finish()
        }

/*var addButton = findViewById<Button>(R.id.buttonAddData)
        addButton.setOnClickListener{
            val map = mapOf(
                "url" to imageUrl,
                "title" to imageTitle
            )

            // Add map to the list
            selectedItemsList.add(map as Map<String, String>)

        }*/

    }
}