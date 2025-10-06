package com.example.frontend_android.ui.AbstractWIP

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class AViewModel<req, resp, R> () : ViewModel() {



    protected abstract val repo: R

    protected abstract suspend fun performAdd(api: R, request: req): Result<Unit>
    protected abstract suspend fun performGet(api: R): Result<List<resp>>
    protected abstract suspend fun performPatch(api: R, data: req): Result<Unit>

    protected abstract suspend fun performGetById(api: R, targetRd: Long): Result<List<resp>>
    protected abstract suspend fun performGetByPairs(api: R, first: Long, second: Long): Result<List<resp>>
    protected abstract suspend fun performRemove(api: R, toRemove: Long, fromTableId: Long): Result<Unit>
    protected abstract suspend fun performSearch(api: R, query:String,value:String): Result<List<resp>>




    protected val _getAllList = MutableStateFlow<List<resp>>(emptyList())
    val getAllList: StateFlow<List<resp>> = _getAllList

    protected val _statusAll = MutableStateFlow<Boolean?>(null)
    val statusAll: StateFlow<Boolean?> =  _statusAll


    protected val _getByIdList = MutableStateFlow<List<resp>>(emptyList())
    val getByIdList: StateFlow<List<resp>> = _getByIdList

    protected val _statusId = MutableStateFlow<Boolean?>(null)
    val statusId: StateFlow<Boolean?> =  _statusId


    protected val _getPairsList = MutableStateFlow<List<resp>>(emptyList())
    val getPairsList: StateFlow<List<resp>> = _getPairsList
    protected val _statusPairs = MutableStateFlow<Boolean?>(null)
    val statusPairs: StateFlow<Boolean?> =  _statusPairs

    protected val _statusRemove = MutableStateFlow<Boolean?>(null)
    val statusRemove: StateFlow<Boolean?> =  _statusRemove

    protected val _statusPost = MutableStateFlow<Boolean?>(null)
    val statusPost: StateFlow<Boolean?> =  _statusPost

    protected val _statusPatch = MutableStateFlow<Boolean?>(null)
    val statusPatch: StateFlow<Boolean?> =  _statusPatch



    protected val _getByQuery = MutableStateFlow<List<resp>>(emptyList())
    val getByQuery: StateFlow<List<resp>> = _getByQuery

    protected val _statusQuery = MutableStateFlow<Boolean?>(null)
    val statusQuery: StateFlow<Boolean?> =  _statusQuery

    fun getByQuery(query: String, value:String){
        viewModelScope.launch {
            val result = performSearch(repo,query,value)
            _statusQuery.value = result.fold(
                onSuccess = { list->
                    _getByQuery.value = list
                    Log.d("TestOneViewModel", " Updated datalist with ${list.size} items")

                    true
                },
                onFailure = {
                    Log.e("Error:", "Error in getAll function", it)
                    false
                }
            )
        }
    }
    fun getAll(){
         viewModelScope.launch {
             val result = performGet(repo)
             _statusAll.value = result.fold(
                 onSuccess = { list->
                     _getAllList.value = list
                     Log.d("TestOneViewModel", " Updated datalist with ${list.size} items")

                     true
                 },
                 onFailure = {
                     Log.e("Error:", "Error in getAll function", it)
                     false
                 }
             )
         }
     }

    override fun onCleared() {
        super.onCleared()
        Log.d("TestOneViewModel", "ViewModel ${this::class.simpleName} destroyed!")
    }

      fun getById(id: Long){
         viewModelScope.launch {
             val result = performGetById(repo, id)
             _statusId.value = result.fold(
                 onSuccess = { list->
                     _getAllList.value = list
                     true
                 },
                 onFailure = {
                     Log.e("Error:", "Error in getAll function", it)
                     false
                 }
             )
         }
     }
       fun getPairsById(first: Long, second:Long){
         viewModelScope.launch {
             val result = performGetByPairs(repo,first,second)
             _statusPairs.value = result.fold(
                 onSuccess = { list->
                     _getPairsList.value = list
                     true
                 },
                 onFailure = {
                     Log.e("Error:", "Error in getAll function", it)
                     false
                 }
             )
         }
     }
    fun add(request: req){
        viewModelScope.launch {
            val result = performAdd(repo,request)
            _statusPost.value = result.fold(
                onSuccess = {
                    true
                },
                onFailure = {
                    Log.e("Error:", "Error in getAll function", it)
                    false
                }
            )
        }
    }
      fun update(request: req){
         viewModelScope.launch {
             val result = performPatch(repo,request)
             _statusPatch.value = result.fold(
                 onSuccess = {
                     true
                 },
                 onFailure = {
                     Log.e("Error:", "Error in getAll function", it)
                     false
                 }
             )
         }
     }
      fun delete(id:Long, tableId: Long){
         viewModelScope.launch {
             val result = performRemove(repo,id,tableId)
             _statusRemove.value = result.fold(
                 onSuccess = {
                     true
                 },
                 onFailure = {
                     Log.e("Error:", "Error in getAll function", it)
                     false
                 }
             )
         }
     }
 }