package com.example.frontend_android.ui.messages

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Chat.MessageRequest
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.MessageRepository
import com.example.frontend_android.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MessageViewModel  @Inject constructor(
    private val repo: MessageRepository,
    private val uRepo: UserRepository
): ViewModel() {
    private val _messages = MutableLiveData<List<MessageResponse>>()
    val messages: LiveData<List<MessageResponse>> = _messages

    private val _users = MutableLiveData<List<UserResponse>>()
    val users: MutableLiveData<List<UserResponse>> = _users

    private val _status = MutableLiveData<Boolean?>(null)
    val state: MutableLiveData<Boolean?> = _status

    private lateinit var request: MessageRequest

    fun searchUsers(query: String, value: String){
        viewModelScope.launch {
            val result = uRepo.searchUsers(query,value)
            result.fold(
                onSuccess = { list ->
                    _users.postValue(list)
                            true
                            },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    false
                }
            )
        }
    }        //aaaaaa


    fun getUsers(){
        viewModelScope.launch {
            val result = uRepo.getAll()
            result.fold(
                onSuccess = { list ->
                    _users.postValue(list)
                    true
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    false
                }
            )
        }
    }

    fun getUserMessages(){
        viewModelScope.launch {
            val result = repo.getData()
            _status.value = result.fold(
                onSuccess = { messageList ->
                    _messages.value = messageList
                    true
                },
                onFailure = {false }
            )
        }
    }

    fun openChat(recipientId: Long) {
        Log.d("Messages", "MESSAGE: TYPE IN TEXT!!!!")
        viewModelScope.launch {
            val result = repo.getDataById(recipientId)
            _status.value = result.fold(
                onSuccess = { messageList ->
                    _messages.value = messageList
                    Log.d("Messages", "MESSAGE: TYPE IN TEXT!!!!$messageList")
                    true
                },
                onFailure = {false }
            )
        }
    }

    fun sendMessage(recipientId: Long, text: String) {

        viewModelScope.launch {
            request = MessageRequest(
                recipientId = recipientId,
                encryptedValue = text
            )
            Log.d("AddReportViewModel: ", "Sent text: ${request.encryptedValue} ")
            Log.d("AddReportViewModel: ", "Sent to: ${request.recipientId} ")
            val result = repo.addData(request)
            _status.value = result.fold(
                onSuccess = { true },
                onFailure = { false }
            )
            Log.d("AddReportViewModel: ", "Response: ${_status.value}")
        }
    }

}