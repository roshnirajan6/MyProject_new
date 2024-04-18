package MyApp.myproject
import MyApp.myproject.SharedPreferenceHelper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import MyApp.myproject.myapi.ApiClient
import MyApp.myproject.myadapter.MyAdapter
import com.example.myproject.R
import MyApp.myproject.mydata.MyDataClass
import android.annotation.SuppressLint
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class DisplayActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {
    private lateinit var myDataList: MutableList<MyDataClass>
    private lateinit var myAdapter: MyAdapter
    private val selectedItemsList = mutableListOf<Map<String, String>>()
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_display)

        sharedPreferenceHelper = SharedPreferenceHelper(this)

        myDataList = ArrayList()
        myAdapter = MyAdapter(myDataList, this) // Pass this as the listener

        val myRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        myRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        myRecyclerView.adapter = myAdapter
        fetchData()

        // Button to add selected items to cart
        val buttonToCart = findViewById<Button>(R.id.buttonToCart)
        buttonToCart.setOnClickListener {
            addToCart()
        }
        val backButton = findViewById<ImageButton>(R.id.imageButtonBackButtonDisplay)
        backButton.setOnClickListener{
            finish()
        }    }

    // Function to fetch data from API
    private fun fetchData() {
        // Create API call using retrofitBuilder
        val makeCall = ApiClient.retrofitBuilder.getData()

        // Enqueue the call to execute asynchronously
        makeCall.enqueue(object : Callback<List<MyDataClass>> {
            override fun onResponse(call: Call<List<MyDataClass>>?, response: Response<List<MyDataClass>>?) {
                // Handle response
                val dataList: List<MyDataClass>? = response?.body()
                if (dataList != null) {
                    Log.d("DataList", dataList.toString())
                    myDataList.clear() // Clear existing data in dataList
                    myDataList.addAll(dataList) // Add new data to dataList
                    myAdapter.setData(dataList) // Update adapter data with new dataList
                }
            }

            override fun onFailure(call: Call<List<MyDataClass>>?, t: Throwable?) {
                Log.i("mytag", "Error is ${t.toString()}")
            }
        })
    }

    override fun onAddButtonClick(data: MyDataClass, position: Int) {
        // Handle "add" button click for each item
        val map = mapOf(
            "url" to data.url,
            "title" to data.title
        )

        // Add map to the list
        selectedItemsList.add(map)
        Toast.makeText(this,"one item added",Toast.LENGTH_LONG).show()
    }

    private fun addToCart() {
        // Save selected items list to SharedPreferences
        sharedPreferenceHelper.saveSelectedItemsList(selectedItemsList)

        // Show a message or perform any other action to indicate that items have been added to cart
        Toast.makeText(this, "Items added to cart", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }
    override fun onItemClick(data: MyDataClass) {//on item clicked move to display activity with url data
        val intent = Intent(this, DataActivity::class.java)
        intent.putExtra("imageUrl", data.url)
        intent.putExtra("title",data.title)
        intent.putExtra("selectedItemsList", ArrayList(selectedItemsList))
        startActivity(intent)

    }
}
