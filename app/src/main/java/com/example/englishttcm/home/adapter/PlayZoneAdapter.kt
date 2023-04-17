/* NAM NV created on 10:30 13-4-2023 */
package com.example.englishttcm.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.englishttcm.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.englishttcm.R
import com.example.englishttcm.home.model.GamePlayMode

class PlayZoneAdapter(
    private var context: Context,
    private val listPlayMode: ArrayList<GamePlayMode>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<PlayZoneAdapter.PlayZoneHolder>() {

    inner class PlayZoneHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val layout: RelativeLayout = itemView.findViewById(R.id.layout_item_play)
        val tvMode: TextView = itemView.findViewById(R.id.tv_game_mode)
        val ivMode: ImageView = itemView.findViewById(R.id.iv_mode)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(listPlayMode[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayZoneHolder =
        PlayZoneHolder(LayoutInflater.from(context).inflate(R.layout.item_game_zone, parent, false))

    override fun getItemCount(): Int = listPlayMode.size

    override fun onBindViewHolder(holder: PlayZoneHolder, position: Int) {
        val studyTitle = listPlayMode[position]
        holder.layout.background = ContextCompat.getDrawable(context, studyTitle.bgColor)
        holder.tvMode.text = studyTitle.mode
        holder.ivMode.setImageResource(studyTitle.image)
    }
}