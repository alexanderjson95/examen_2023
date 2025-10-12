package com.example.frontend_android.ui.conversations

import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Chat.MessageRequest
import com.example.frontend_android.model.Chat.MessageRequestPatch
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.RoleRequest
import com.example.frontend_android.repository.MessageRepository
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.ui.AbstractWIP.AViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//@HiltViewModel
//open class MessagesViewModel @Inject constructor(
//    override val repo: MessageRepository,
//    private val uRepo: UserRepository
//): AViewModel<MessageRequest, MessageResponse, MessageRepository>() {
//
//
//    /**
//     * Eftersom jag inte vill skapa DTO objekt i UI så
//     * skapar jag dessa helper funktionerna
//     */
//    companion object {
//        fun mapMessageRequest(recipientId: Long, content: String): MessageRequest{
//            return MessageRequest(recipientId,content)
//        }
//    }
//
//
//    fun sendMessage(recipientId: Long, text: String) {
//        val req = mapMessageRequest(recipientId,text)
//        add(req)
//    }
//
//    private val _contacts = MutableStateFlow<List<UserResponse>>(emptyList())
//    val contacts: StateFlow<List<UserResponse>> = _contacts
//    private val _contactStatus = MutableStateFlow<Boolean?>(null)
//    val contactStatus: StateFlow<Boolean?> = _contactStatus
//
//    fun getContacts(){
//        viewModelScope.launch {
//            val result = repo.getContacts()
//            _contactStatus.value = result.fold(
//                onSuccess = { contactList ->
//                    _contacts.value = contactList
//                    true
//                },
//                onFailure = {false }
//            )
//        }
//    }
//
//
//    override suspend fun performAdd(
//        api: MessageRepository,
//        data: MessageRequest
//    ): Result<Unit> {
//        return repo.addData(data)
//    }
//    override suspend fun performGet(api: MessageRepository): Result<List<MessageResponse>> {
//        return repo.getData()
//    }
//
//    override suspend fun performPatch(
//        api: MessageRepository,
//        data: MessageRequestPatch
//    ): Result<Unit> {
//        return repo.updateData(data)
//    }
//
//    override suspend fun performGetById(
//        api: MessageRepository,
//        targetRd: Long
//    ): Result<List<MessageResponse>> {
//        return repo.getDataById(targetRd)
//    }
//
//    override suspend fun performGetByPairs(
//        api: MessageRepository,
//        first: Long,
//        second: Long
//    ): Result<List<MessageResponse>> {
//        return repo.getDataByPair(first,second)
//    }
//
//    override suspend fun performRemove(
//        api: MessageRepository,
//        toRemove: Long,
//        fromTableId: Long
//    ): Result<Unit> {
//        return repo.deleteData(toRemove,fromTableId)
//    }
//
//    override suspend fun performSearch(
//        api: MessageRepository,
//        query: String,
//        value: String
//    ): Result<List<MessageResponse>> {
//        TODO("Not yet implemented")
//    }
//
//
//}