package MyApp.myproject.myadapter

import MyApp.myproject.CartActivity
import MyApp.myproject.SharedPreferenceHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myproject.R

class CartAdapter(
    private var itemList: MutableList<Map<String, String>>,
    private val sharedPreferenceHelper: SharedPreferenceHelper
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val aggregatedItemList = mutableListOf<Map<String, String>>()

    init {
        aggregateItems()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewImg: ImageView = itemView.findViewById(R.id.imageViewProduct)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewMyTitle)
        val textViewQuantity: TextView = itemView.findViewById(R.id.textViewQuantity)
       /* val removeButton: Button = itemView.findViewById(R.id.buttonRemove)*/
    }

    private fun aggregateItems() {
        val itemMap = mutableMapOf<String, Pair<String, Int>>()
        for (item in itemList) {
            val title = item["title"].toString()
            val quantity = itemMap[title]?.second ?: 0
            itemMap[title] = Pair(title, quantity + 1)
        }
        for ((title, pair) in itemMap) {
            val aggregatedItem = mapOf(
                "title" to pair.first,
                "quantity" to pair.second.toString(),
                "url" to itemList.firstOrNull { it["title"] == title }?.get("url").toString()
            )
            aggregatedItemList.add(aggregatedItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = aggregatedItemList[position]
        val imageUrl = item["url"]
        val imageTitle = item["title"]
        val quantity = item["quantity"]
        imageUrl?.let {
            Glide.with(holder.itemView)
                .load(it)
                .into(holder.imageViewImg)
        }
        holder.textViewTitle.text = imageTitle
        holder.textViewQuantity.text = "Quantity: $quantity"

        /*holder.removeButton.setOnClickListener {
            sharedPreferenceHelper.removeItemFromList(position)
            updateData(sharedPreferenceHelper.getSelectedItemsList())

        }*/
    }

    fun updateData(newItemList: List<Map<String, String>>) {
        itemList.clear() // Clear the current dataset
        itemList.addAll(newItemList) // Add new items to the dataset
        notifyDataSetChanged() // Notify adapter of dataset change
    }
    fun clearItems() {

        itemList.clear()

        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = aggregatedItemList.size
}
