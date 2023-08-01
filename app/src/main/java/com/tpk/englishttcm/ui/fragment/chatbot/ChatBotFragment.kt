package com.tpk.englishttcm.ui.fragment.chatbot

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.callback.OnCallApiCompleteCallBack
import com.tpk.englishttcm.ui.adapter.MessageAdapter
import com.tpk.englishttcm.data.remote.api.response.Message
import com.tpk.englishttcm.viewmodel.ChatBotViewModel
import com.tpk.englishttcm.databinding.FragmentChatBotBinding

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
                object : OnCallApiCompleteCallBack {
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