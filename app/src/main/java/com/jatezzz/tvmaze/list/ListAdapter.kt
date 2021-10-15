package com.jatezzz.tvmaze.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.list.response.ShowsItem

class ListAdapter(
    context: Context,
    val onClickAction: (ShowsItem) -> Unit,
    val onDeleteAction: (ShowsItem) -> Unit = {},
    private val shouldAllowDeletion: Boolean = false,
    private var shows: ArrayList<ShowsItem> = ArrayList()
) :
    RecyclerView.Adapter<ListAdapter.ShowViewHolder>() {

    override fun getItemCount(): Int = shows.size

    private var glide: RequestManager = Glide.with(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        val holder = ShowViewHolder(view)
        holder.itemView.setOnClickListener {
            onClickAction(shows[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = shows[position]
        holder.text.text = show.name
        holder.deleteButton.visibility = if (shouldAllowDeletion) View.VISIBLE else View.GONE
        holder.deleteButton.setOnClickListener {
            onDeleteAction(show)
        }
        show.image?.medium?.let { glide.load(it).into(holder.image) }
    }

    fun setData(data: List<ShowsItem>) {
        val lastPosition = shows.size
        shows = ArrayList()
        notifyItemRangeRemoved(0, lastPosition)
        shows.addAll(data)
        notifyItemRangeInserted(0, shows.size)
    }

    fun addData(data: Collection<ShowsItem>) {
        val lastPosition = shows.size
        shows.addAll(data)
        notifyItemRangeInserted(lastPosition, data.size)
    }

    class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.image)
        val deleteButton: Button = itemView.findViewById(R.id.button_delete)
    }

}
