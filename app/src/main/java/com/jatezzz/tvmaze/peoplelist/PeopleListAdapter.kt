package com.jatezzz.tvmaze.peoplelist

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
import com.jatezzz.tvmaze.peoplelist.response.Person

class PeopleListAdapter(
    context: Context,
    val onClickAction: (Person) -> Unit,
    private var datalist: ArrayList<Person> = ArrayList()
) :
    RecyclerView.Adapter<PeopleListAdapter.ShowViewHolder>() {

    override fun getItemCount(): Int = datalist.size

    private var glide: RequestManager = Glide.with(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        val holder = ShowViewHolder(view)
        holder.itemView.setOnClickListener {
            onClickAction(datalist[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = datalist[position]
        holder.text.text = show.name
        show.image?.medium?.let { glide.load(it).into(holder.image) }
    }

    fun setData(data: List<Person>) {
        val lastPosition = datalist.size
        datalist = ArrayList()
        notifyItemRangeRemoved(0, lastPosition)
        datalist.addAll(data)
        notifyItemRangeInserted(0, datalist.size)
    }

    class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

}
