//package com.example.frontend_android.ui.AbstractWIP
//
//import android.util.Log
//import com.example.frontend_android.model.Chat.MessageRequest
//import com.example.frontend_android.model.Chat.MessageResponse
//import com.example.frontend_android.repository.MessageRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class TestOneViewModel @Inject constructor(
//    override val repo: MessageRepository
//): AViewModel<MessageRequest, MessageResponse, MessageRepository>() {
//
//
//    override suspend fun performAdd(
//        api: MessageRepository,
//        data: MessageRequest
//    ): Result<Unit> {
//        return repo.addData(data)
//    }
//
//
//    override suspend fun performGet(api: MessageRepository): Result<List<MessageResponse>> {
//        return repo.getData()
//    }
//
//    override suspend fun performPatch(
//        api: MessageRepository,
//        data: MessageRequest
//    ): Result<Unit> {
//        return repo.updateData(data)
//    }
//
//    override suspend fun performGetById(
//        api: MessageRepository,
//        targetId: Long
//    ): Result<List<MessageResponse>> {
//       return repo.getDataById(targetId)
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
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun performSearch(
//        api: MessageRepository,
//        query: String
//    ): Result<List<MessageResponse>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.d("AViewModel",  "ViewModel ${this::class.simpleName} destroyed!")
//    }
//
//
//}