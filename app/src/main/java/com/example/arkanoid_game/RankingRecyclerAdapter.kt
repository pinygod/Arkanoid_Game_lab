package com.example.arkanoid_game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.arkanoid_game.data.RankingItem

class RankingRecyclerAdapter(
    private var items: ArrayList<RankingItem>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class NoteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<View>(R.id.personName) as TextView
        var score = itemView.findViewById(R.id.personScore) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ranking_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as NoteViewHolder).name.text = item.name
        holder.score.text = item.result?.score.toString()
    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(list: ArrayList<RankingItem>) {
        this.items.clear()
        this.items.addAll(list)
        this.notifyDataSetChanged()
    }
}