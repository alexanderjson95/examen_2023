package com.example.frontend_android.ui.conversations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.ErrorMessages
import com.example.frontend_android.model.Chat.MessageRequest
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.MessageRepository
import com.example.frontend_android.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MessageViewModel  @Inject constructor(
    private val repo: MessageRepository,
    private val uRepo: UserRepository
): ViewModel() {
    private val _messages = MutableStateFlow<List<MessageResponse>>(emptyList())
    val messages: StateFlow<List<MessageResponse>> = _messages


    private val _users = MutableStateFlow<List<UserResponse>>(emptyList())
    val users: StateFlow<List<UserResponse>> = _users

    private val _contacts = MutableStateFlow<List<UserResponse>>(emptyList())
    val contacts: StateFlow<List<UserResponse>> = _contacts


    private val _status = MutableStateFlow<Boolean?>(null)
    val state: StateFlow<Boolean?> = _status

    private lateinit var request: MessageRequest

    private val name = "MessageViewModel"
    private val dataA = "Users"
    private val dataB = "Messages"



    fun searchUsers(query: String, value: String){
        viewModelScope.launch {
            val result = uRepo.searchUsers(query,value)
            result.fold(
                onSuccess = { list ->
                    _users.value =  list
                            true
                            },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    ErrorMessages.get_error(name, dataA)
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
                    _users.value =  list
                    true
                },
                onFailure = { e ->
                    ErrorMessages.get_error(name, dataA)
                    false
                }
            )
        }
    }

    fun getUserMessages(){
        viewModelScope.launch {
            val result = repo.getContacts()
            _status.value = result.fold(
                onSuccess = { contactList ->
                    _contacts.value = contactList
                    true
                },
                onFailure = {
                    ErrorMessages.get_error(name, dataB)
                    false
                }
            )
        }
    }



    fun openChat(recipientId: Long) {
        viewModelScope.launch {
            val result = repo.getDataById(recipientId)
            _status.value = result.fold(
                onSuccess = { messageList ->
                    _messages.value = messageList
                    true
                },
                onFailure = {
                    ErrorMessages.get_error(name, dataB)
                    false
                }
            )
        }
    }

    fun sendMessage(recipientId: Long, text: String) {
        viewModelScope.launch {
            request = MessageRequest(
                recipientId = recipientId,
                encryptedValue = text
            )
            val result = repo.addData(request)
            _status.value = result.fold(
                onSuccess = {
                    openChat(recipientId)
                    true

                            },
                onFailure = {
                    ErrorMessages.get_error(name, dataB)
                    false }
            )
        }
    }



}