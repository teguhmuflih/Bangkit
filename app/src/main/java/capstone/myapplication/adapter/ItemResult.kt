package capstone.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import capstone.myapplication.R
import capstone.myapplication.data.DataPhoto

class ItemResult(private val context: Context, private val itemResult: List<DataPhoto>) : RecyclerView.Adapter<ItemResult.ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.items_photo_result, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        itemResult[position].photo?.let { holder.itemImage.setImageResource(it) }
    }

    override fun getItemCount(): Int {
        return itemResult.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView

        init {
            itemImage = itemView.findViewById(R.id.item_photo_result)
        }

    }



}