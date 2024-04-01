package com.example.myproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


    class MyAdapter (private val dataList: ArrayList<MyDataClass>) : RecyclerView.Adapter<MyAdapter.ViewHolderClass>(){
        class ViewHolderClass(itemView: View):RecyclerView.ViewHolder(itemView) {
            val myImageView: ImageView = itemView.findViewById(R.id.myImage)
            val myTitle: TextView = itemView.findViewById(R.id.myTitle)


        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
            return ViewHolderClass(itemView)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            val currentItem=dataList[position]
            holder.myImageView.setImageResource(currentItem.image)
            holder.myTitle.text=currentItem.title

        }


    }