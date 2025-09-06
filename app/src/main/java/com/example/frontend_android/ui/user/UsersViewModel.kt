package com.example.frontend_android.ui.user

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
class UsersViewModel  @Inject constructor(
    private val repo: UserRepository
): ViewModel() {
    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> = _users
    private val _status = MutableLiveData<String?>(null)
    val state: MutableLiveData<String?> = _status


    fun returnUserByUsername(username: String): UserResponse? {
        val u = users.value?.find { it.username == username }
        Log.e("AllProjectsViewModel", "Found: ${u?.username} with ID: ${u?.id}")
        return u
    }

    fun searchUsers(query: String, value: String){
        viewModelScope.launch {
            val result = repo.searchUsers(query,value)
            result.fold(
                onSuccess = { list ->
                    _users.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    _status.value = "error"
                }
            )
        }
    }

    fun getUsers(){
        viewModelScope.launch {
            val result = repo.getAll()
            result.fold(
                onSuccess = { list ->
                    _users.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    _status.value = "error"
                }
            )
        }
    }


}