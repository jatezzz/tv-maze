package com.jatezzz.tvmaze.show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jatezzz.tvmaze.R

class GenreAdapter(private var shows: ArrayList<String> = ArrayList()) :
    RecyclerView.Adapter<GenreAdapter.ShowViewHolder>() {

    override fun getItemCount(): Int = shows.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = shows[position]
        holder.text.text = show
    }

    fun setData(data: List<String>) {
        val lastPosition = shows.size
        shows = ArrayList()
        notifyItemRangeRemoved(0, lastPosition)
        shows.addAll(data)
        notifyItemRangeInserted(0, shows.size)
    }


    class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.name)
    }

}
