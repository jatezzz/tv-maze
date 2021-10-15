package com.jatezzz.tvmaze.show

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jatezzz.tvmaze.R

class EpisodeAdapter(
    context: Context,
    val onClickAction: (ShowViewModel.EpisodeViewData) -> Unit,
    private var episodes: ArrayList<ShowViewModel.EpisodeViewData> = ArrayList()
) :
    RecyclerView.Adapter<EpisodeAdapter.ShowViewHolder>() {

    override fun getItemCount(): Int = episodes.size

    private var glide: RequestManager = Glide.with(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = episodes[position]
        holder.text.text = show.name
        glide.load(show.imageUrl).into(holder.image)
        holder.itemView.setOnClickListener {
            onClickAction(show)
        }
    }

    fun setData(data: List<ShowViewModel.EpisodeViewData>) {
        val lastPosition = episodes.size
        episodes = ArrayList()
        notifyItemRangeRemoved(0, lastPosition)
        episodes.addAll(data)
        notifyItemRangeInserted(0, episodes.size)
    }

    class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

}
