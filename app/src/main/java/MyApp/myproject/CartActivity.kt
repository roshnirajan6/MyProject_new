package MyApp.myproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myproject.R
import MyApp.myproject.SharedPreferenceHelper
import MyApp.myproject.myadapter.CartAdapter
import MyApp.myproject.myadapter.MyAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
import kotlin.concurrent.thread


class CartActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var sharedPreferences: SharedPreferenceHelper
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        sharedPreferences = SharedPreferenceHelper(this)
        val selectedItemsList = sharedPreferences.getSelectedItemsList()
        val clearButton = findViewById<Button>(R.id.buttonClear)
        val addMoreButton = findViewById<Button>(R.id.buttonAddMore)

        addMoreButton.setOnClickListener{
        /*val intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)*/
            finish()
        }
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(selectedItemsList, sharedPreferences)
        recyclerView.adapter = cartAdapter
        clearButton.setOnClickListener{

Handler().post {
    // Update the adapter with an empty list
    val emptyList = emptyList<Map<String, String>>()
    cartAdapter.updateData(emptyList)
    recyclerView.adapter = cartAdapter
    // Display a message to inform the user
    Toast.makeText(this, "Cleared the cart", Toast.LENGTH_LONG).show()
}
        }
    }

}