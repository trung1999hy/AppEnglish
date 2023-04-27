package com.example.englishttcm.chatbot.view

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.chatbot.OnCallApiCompleteListener
import com.example.englishttcm.chatbot.adapter.MessageAdapter
import com.example.englishttcm.chatbot.model.Message
import com.example.englishttcm.chatbot.viewmodel.ChatBotViewModel
import com.example.englishttcm.databinding.FragmentChatBotBinding

class ChatBotFragment : BaseFragment<FragmentChatBotBinding>() {
    private var chatBotViewModel: ChatBotViewModel? = null
    override fun getLayout(container: ViewGroup?): FragmentChatBotBinding =
        FragmentChatBotBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        chatBotViewModel = ViewModelProvider(this)[ChatBotViewModel::class.java]
        binding.btnSend.setOnClickListener {
            val content = binding.edtMessage.text.toString()
            addMessage(content, true)
            chatBotViewModel!!.sendMessageToBot(
                content,
                object : OnCallApiCompleteListener {
                    override fun onSuccess(data: Any?) {
                        addMessage(data as String, false)
                    }

                    override fun onFailed(data: Any?) {
                        addMessage(data as String, false)
                    }

                })
            binding.edtMessage.setText("")
        }
        chatBotViewModel!!.listMessageLive.observe(viewLifecycleOwner) {
            binding.rcvChat.adapter = MessageAdapter(it)
            val itemCount = binding.rcvChat.adapter?.itemCount ?: 0
            binding.rcvChat.smoothScrollToPosition(itemCount)
        }
        binding.ivBack.setOnClickListener {
            callback.backToPrevious()
        }
    }

    private fun addMessage(content: String, isSendByMe: Boolean) {
        val message = Message(content, isSendByMe)
        chatBotViewModel!!.addMessage(message)
    }
}