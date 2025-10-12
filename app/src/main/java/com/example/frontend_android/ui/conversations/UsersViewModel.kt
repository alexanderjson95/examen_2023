package com.example.frontend_android.ui.conversations

import com.example.frontend_android.model.Chat.MessageRequest
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserRequestPatch
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.RoleRequest
import com.example.frontend_android.repository.MessageRepository
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.ui.AbstractWIP.AViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//@HiltViewModel
//open class UsersViewModel @Inject constructor(
//    override val repo: UserRepository,
//): AViewModel<UserRequest, UserResponse, UserRepository>() {
//
//
//    companion object {
//        fun mapUserRequest(
//            username: String, firstName: String, lastName: String,
//                           pasword:String, email:String, publicKey:String,
//            roles:List<RoleRequest>): UserRequest{
//            return UserRequest(
//                username = username,
//                firstName = firstName,
//                lastName = lastName,
//                password = pasword,
//                email = email,
//                publicKey = publicKey,
//                roles = roles
//            )
//        }
//    }
//
//
//    fun createUser(
//        username: String, firstName: String, lastName: String,
//        pasword:String, email:String, publicKey:String,
//        roles:List<RoleRequest>) {
//        val req = mapUserRequest(username,firstName,lastName,pasword,email,publicKey,roles)
//        add(req)
//    }
//
//
//
//
//
//
//    override suspend fun performAdd(
//        api: UserRepository,
//        data: UserRequest
//    ): Result<Unit> {
//        return repo.addData(data)
//    }
//
//    override suspend fun performGet(api: UserRepository): Result<List<UserResponse>> {
//        return api.getAll()
//    }
//
//    override suspend fun performPatch(
//        api: UserRepository,
//        data: UserRequestPatch
//    ): Result<Unit> {
//        return api.updateData(data)
//    }
//
//    override suspend fun performGetById(
//        api: UserRepository,
//        targetRd: Long
//    ): Result<List<UserResponse>> {
//
//        return api.getDataById(targetRd)
//    }
//
//    override suspend fun performGetByPairs(
//        api: UserRepository,
//        first: Long,
//        second: Long
//    ): Result<List<UserResponse>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun performRemove(
//        api: UserRepository,
//        toRemove: Long,
//        fromTableId: Long
//    ): Result<Unit> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun performSearch(
//        api: UserRepository,
//        query: String,
//        value: String
//    ): Result<List<UserResponse>> {
//        TODO("Not yet implemented")
//    }
//
//
//}