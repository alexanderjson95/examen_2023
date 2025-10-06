//package com.example.frontend_android.ui.AbstractWIP
//
//import android.util.Log
//import com.example.frontend_android.model.Projects.UserProjectRequest
//import com.example.frontend_android.model.Projects.UserProjectResponse
//import com.example.frontend_android.repository.UserProjectRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//
//@HiltViewModel
//class TestTwoViewModel @Inject constructor(
//    override val repo: UserProjectRepository
//): AViewModel<UserProjectRequest, UserProjectResponse, UserProjectRepository>() {
//
//
//    override suspend fun performAdd(
//        api: UserProjectRepository,
//        data: UserProjectRequest
//    ): Result<Unit> {
//        return repo.addData(data)
//    }
//
//    override suspend fun performGet(api: UserProjectRepository): Result<List<UserProjectResponse>> {
//        return repo.getData()
//    }
//
//    override suspend fun performPatch(
//        api: UserProjectRepository,
//        data: UserProjectRequest
//    ): Result<Unit> {
//        return repo.updateData(data)
//    }
//
//    override suspend fun performGetById(
//        api: UserProjectRepository,
//        targetRd: Long
//    ): Result<List<UserProjectResponse>> {
//        return repo.getDataById(targetRd)
//    }
//
//    override suspend fun performGetByPairs(
//        api: UserProjectRepository,
//        first: Long,
//        second: Long
//    ): Result<List<UserProjectResponse>> {
//        return repo.getDataByPair(first,second)
//
//    }
//
//    override suspend fun performRemove(
//        api: UserProjectRepository,
//        toRemove: Long,
//        fromTableId: Long
//    ): Result<Unit> {
//        return repo.deleteData(toRemove, fromTableId)
//    }
//
//    override suspend fun performSearch(
//        api: UserProjectRepository,
//        query: String
//    ): Result<List<UserProjectResponse>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.d("AViewModel",  "ViewModel ${this::class.simpleName} destroyed!")
//    }
//
//}