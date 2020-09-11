package com.nurbk.ps.noteapp.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.noteapp.R
import com.nurbk.ps.noteapp.data.models.Note
import kotlinx.android.synthetic.main.item_container_note.view.*

class NoteAdapter(
    var data: ArrayList<Note>,
    val onClick: OnItemClick
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(item: View) : RecyclerView.ViewHolder(item)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = data[position]
        holder.itemView.apply {
            textTitle.text = note.title
            textSubtitle.text = note.subtitle
            textDateTime.text = note.dateTime

            val gradientDrawable = layoutNote.background as GradientDrawable
            gradientDrawable.setColor(Color.parseColor(note.color!!))

            setOnClickListener {
                onClick.onClickItem(note)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_container_note, parent, false
            )
        )
    }


    interface OnItemClick {
        fun onClickItem(note: Note)
    }

}