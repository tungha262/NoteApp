package com.example.noteapp.presentation.ui.fragment.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.data.model.Note
import com.example.noteapp.util.Const

class HomeNoteAdapter : RecyclerView.Adapter<HomeNoteAdapter.HomeViewHolder>() {
    private var listNotes = emptyList<Note>()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Note>) {
        this.listNotes = list
        notifyDataSetChanged()
    }


    inner class HomeViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvTitle: TextView = item.findViewById(R.id.tv_note_add_tilte)
        val tvContent: TextView = item.findViewById(R.id.tv_note_add_content)
        val tvDate: TextView = item.findViewById(R.id.tv_note_add_date)
        val imgPriority: ImageView = item.findViewById(R.id.img_note_add_priority)
        val rowItem : CardView = item.findViewById(R.id.note_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val note: Note = listNotes[position]
        holder.apply {
            tvTitle.text = note.title
            tvContent.text = note.content
            tvDate.text = note.date
            when (note.priority) {
                Const.HIGH_PRIORITY -> {
                    imgPriority.setImageResource(R.drawable.red_dot)
                }

                Const.MEDIUM_PRIORITY -> {
                    imgPriority.setImageResource(R.drawable.yellow_dot)
                }

                else -> {
                    imgPriority.setImageResource(R.drawable.green_dot)
                }
            }
        }
        holder.rowItem.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(note)
            it.findNavController().navigate(action)
        }
    }
}