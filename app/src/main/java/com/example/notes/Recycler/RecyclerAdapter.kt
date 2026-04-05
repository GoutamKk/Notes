package com.example.notes.Recycler

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.appendPlaceholders
import com.example.notes.Database.NotesData
import com.example.notes.Database.RoomDB
import com.example.notes.Model.MainVMFactory
import com.example.notes.Model.MainViewModel
import com.example.notes.Model.Repository
import com.example.notes.R

class RecyclerAdapter(private val onClick:(NotesData)-> Unit, private val onDelete:(NotesData) -> Unit): RecyclerView.Adapter<RecyclerAdapter.Viewholder>() {

    private var list = ArrayList<NotesData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): Viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_layout, parent,false)
        return Viewholder(view)
    }
    class Viewholder(view:View): RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.item_title)
        val text = view.findViewById<TextView>(R.id.item_text)
        val time = view.findViewById<TextView>(R.id.item_time)
    }
    override fun onBindViewHolder(
        holder: Viewholder,
        position: Int,
    ) {
        holder.apply {
            title.text = list[position].title.toString()
            text.text = list[position].text.toString()
            time.text = getTimeAgo(list[position].time.toString().toLong())
        }
        holder.itemView.setOnClickListener {
            onClick(list[position])
        }
        holder.itemView.setOnLongClickListener { t ->
            onDelete(list[position])
            true
        }
    }

    override fun getItemCount(): Int {
         return list.size
    }

    fun setNotes(notesData: List<NotesData>){
        list.clear()
        list.addAll(notesData)
        notifyDataSetChanged()
    }

    fun getTimeAgo(time: Long): String {
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) return "Just now"

        val diff = now - time

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        return when {
            seconds < 60 -> "Just now"
            minutes < 60 -> "$minutes min ago"
            hours < 24   -> "$hours hrs ago"
            days < 7     -> "$days days ago"
            weeks < 4    -> "$weeks weeks ago"
            months < 12  -> "$months months ago"
            else         -> "$years years ago"
        }
    }

}



