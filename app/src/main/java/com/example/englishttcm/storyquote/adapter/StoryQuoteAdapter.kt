
/* NAM NV created on 10:50 13-4-2023 */
package com.example.englishttcm.storyquote.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.englishttcm.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishttcm.R
import com.example.englishttcm.storyquote.model.StoryQuote

class StoryQuoteAdapter(
    private val context: Context,
    private val listStoryQuote: ArrayList<StoryQuote>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<StoryQuoteAdapter.StoryQuoteHolder>() {

    inner class StoryQuoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(listStoryQuote[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryQuoteHolder =
        StoryQuoteHolder(LayoutInflater.from(context).inflate(R.layout.item_story_and_quote, parent, false))

    override fun getItemCount(): Int = listStoryQuote.size

    override fun onBindViewHolder(holder: StoryQuoteHolder, position: Int) {
        val storyQuote = listStoryQuote[position]
        holder.tvTitle.text = storyQuote.title
    }

}