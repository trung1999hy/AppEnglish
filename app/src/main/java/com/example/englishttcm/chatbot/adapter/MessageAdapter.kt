package com.example.englishttcm.chatbot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishttcm.chatbot.model.Message
import com.example.englishttcm.databinding.ItemChatBinding

class MessageAdapter(
    private val listMessage: List<Message>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){

    inner class MessageViewHolder (private val bind: ItemChatBinding) : RecyclerView.ViewHolder(bind.root){
        fun binding(message: Message){
            if(message.isSendByMe){
                bind.cdMessageBot.visibility = View.GONE
                bind.cdMessageHuman.visibility = View.VISIBLE
                bind.txtMessageHuman.text = message.message
            } else {
                bind.cdMessageHuman.visibility = View.GONE
                bind.cdMessageBot.visibility = View.VISIBLE
                bind.txtMessageBot.text = message.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemChatBinding.inflate(inflater, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = listMessage.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.binding(listMessage[position])
    }
}