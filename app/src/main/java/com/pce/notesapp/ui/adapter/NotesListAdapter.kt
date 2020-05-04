package com.pce.notesapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pce.notesapp.R
import com.pce.notesapp.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NotesListAdapter :
    ListAdapter<Note, NotesListAdapter.NoteViewHolder>(object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }) {

    var listener: OnItemClickListener? = null

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Note) = with(itemView) {
            noteTitle.text = item.title
            noteDescription.text = item.description
            notePriority.text = item.priority.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (position != RecyclerView.NO_POSITION) {
            holder.itemView.setOnClickListener {
                listener?.onClick(getItem(position))
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(item: Note)
    }
}

