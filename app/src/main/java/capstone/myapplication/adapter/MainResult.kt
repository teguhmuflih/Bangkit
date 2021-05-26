package capstone.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.myapplication.R
import capstone.myapplication.data.DataEntity
import capstone.myapplication.data.DataPhoto

class MainResult(private val context: Context, private val dataEntity: List<DataEntity>?) : RecyclerView.Adapter<MainResult.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.items_result, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.pestName.text = dataEntity?.get(position)?.name
        holder.percentage.text = dataEntity?.get(position)?.percentage
        dataEntity?.get(position)?.let { setItemRecycler(holder.lvPhoto, it.dataPhoto) }
    }

    override fun getItemCount(): Int {
        return dataEntity!!.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pestName: TextView
        var percentage: TextView
        var lvPhoto: RecyclerView

        init {
            pestName = itemView.findViewById(R.id.name_pest)
            percentage = itemView.findViewById(R.id.percentage)
            lvPhoto = itemView.findViewById(R.id.lv_photo)
        }
    }

    private fun setItemRecycler(recyclerView: RecyclerView, dataPhotoList: List<DataPhoto>){
        val itemRecyclerAdapter = context?.let { ItemResult(it, dataPhotoList) }
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = itemRecyclerAdapter
    }

}