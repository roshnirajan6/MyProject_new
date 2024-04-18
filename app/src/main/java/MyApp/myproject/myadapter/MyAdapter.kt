package MyApp.myproject.myadapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myproject.R
import MyApp.myproject.mydata.MyDataClass
import android.widget.Button

class MyAdapter(
    private var dataList: MutableList<MyDataClass>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Interface to handle item click events
    interface OnItemClickListener {
        fun onAddButtonClick(data: MyDataClass, position: Int)
        fun onItemClick(data: MyDataClass)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.myTitle)
        var image: ImageView = itemView.findViewById(R.id.imageViewMyprofile)
        var addButton: Button = itemView.findViewById(R.id.buttonAdd)

        fun bind(data: MyDataClass) {
            title.text = data.title
            Glide.with(itemView.context)
                .load(data.url)
                .into(image)

            // Set OnClickListener for the "add" button
            addButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onAddButtonClick(dataList[position], position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(data)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(newDataList: List<MyDataClass>) {
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }
}