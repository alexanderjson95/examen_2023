package com.example.frontend_android.ui.messages

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Chat.MessageRequest
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MessageViewModel  @Inject constructor(
    private val repo: MessageRepository
): ViewModel() {
    private val _messages = MutableLiveData<List<MessageResponse>>()
    val messages: LiveData<List<MessageResponse>> = _messages
    private val _status = MutableLiveData<Boolean?>(null)
    val state: MutableLiveData<Boolean?> = _status

    private lateinit var request: MessageRequest


    fun getUserMessages(){
        Log.d("MovieLike", "hey")
        viewModelScope.launch {
            val result = repo.getAllMessages()
            _status.value = result.fold(
                onSuccess = { messageList ->
                    _messages.value = messageList
                    true
                },
                onFailure = {false } //todo felhantering här
            )
        }
    }

    fun sendMessage(recipientId: Long?, text: String) {
        viewModelScope.launch {
            request = MessageRequest(
                recipientId = recipientId,
                encryptedValue = text
            )
            Log.d("AddReportViewModel: ", "Sent to: ${request.recipientId} ")
            val result = repo.sendMessage(request)
            _status.value = result.fold(
                onSuccess = { true },
                onFailure = { false }
            )
            Log.d("AddReportViewModel: ", "Response: ${_status.value}")
        }
    }

}