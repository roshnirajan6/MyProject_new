package com.example.myproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisplayActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<MyDataClass>
    private lateinit var imageList:Array<Int>
    private lateinit var titleList:Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_display)

        imageList= arrayOf(
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8
        )

        titleList= arrayOf(
            "pic1",
            "pic2",
            "pic3",
            "pic4",
            "pic5",
            "pic6",
            "pic7",
            "pic8"
        )

        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList= arrayListOf<MyDataClass>()
        getData()
 /*       recyclerView.setOnClickListener {
                val intent = Intent(recyclerView.context, DataActivity::class.java)
                intent.putExtra("title", title)
                recyclerView.context.startActivity(intent)
            }*/
        }

/*
     fun onItemClick(position: Int) {
        val item = dataList[position]
        val intent = Intent(this, DataActivity::class.java)
        intent.putExtra("title", item.title)
        startActivity(intent)
    }*/
    private fun getData(){
        for (i in imageList.indices){
            val dataClass=MyDataClass(imageList[i],titleList[i])
            dataList.add(dataClass)
        }
        recyclerView.adapter=MyAdapter(dataList)
    }


}