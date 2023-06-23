package com.example.englishttcm.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishttcm.learnzone.vocabulary.OnItemWordClickListener
import com.example.englishttcm.bookmark.model.BookmarkWord
import com.tpk.englishttcm.databinding.ItemBookmarkBinding

class BookmarkAdapter(
    var listWord : ArrayList<BookmarkWord>,
    private val clickListener: OnItemWordClickListener
): RecyclerView.Adapter<BookmarkAdapter.NotebookViewHolder>() {
    inner class NotebookViewHolder(val binding: ItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word : BookmarkWord) {
            binding.txtNameWord.text = word.word
            binding.txtMean.text = word.mean
            binding.txtExample.text = word.example
            binding.txtPronounce.text = word.pronounce
        }
        init {
            binding.speaker.setOnClickListener {
                clickListener.onItemClick(listWord[adapterPosition])
            }
            binding.btnDelete.setOnClickListener {
                clickListener.onItemNoteClick(listWord[adapterPosition])
            }
            binding.btnRepair.setOnClickListener {
                clickListener.onItemEditClick(listWord[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemBookmarkBinding.inflate(inflater, parent, false)
        return NotebookViewHolder(view)
    }

    override fun getItemCount(): Int = listWord.size

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        holder.bind(listWord[position])
    }

    fun removeItem(position: Int) {
        listWord.removeAt(position)
        notifyItemRemoved(position)
    }
    fun updateData(position: Int, newData: BookmarkWord) {
        listWord[position] = newData
        notifyItemChanged(position)
    }

}