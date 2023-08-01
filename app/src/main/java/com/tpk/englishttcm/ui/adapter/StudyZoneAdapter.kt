/* NAM NV created on 10:00 13-4-2023 */
package com.tpk.englishttcm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.tpk.englishttcm.callback.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.tpk.englishttcm.model.StudyMode
import com.tpk.englishttcm.R

class StudyZoneAdapter(
    private var context: Context,
    private val listStudyTitle: ArrayList<StudyMode>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<StudyZoneAdapter.StudyZoneHolder>() {

    inner class StudyZoneHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val layout: RelativeLayout = itemView.findViewById(R.id.layout_item_study)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title_study)
        val ivTitle: ImageView = itemView.findViewById(R.id.iv_title)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(listStudyTitle[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyZoneHolder =
        StudyZoneHolder(LayoutInflater.from(context).inflate(R.layout.item_study_zone, parent, false))

    override fun getItemCount(): Int = listStudyTitle.size

    override fun onBindViewHolder(holder: StudyZoneHolder, position: Int) {
        val studyTitle = listStudyTitle[position]
        holder.layout.background = ContextCompat.getDrawable(context, studyTitle.bgColor)
        holder.tvTitle.text = studyTitle.title
        holder.ivTitle.setImageResource(studyTitle.image)
    }
}