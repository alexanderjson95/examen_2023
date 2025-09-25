package com.example.frontend_android.repository

import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.roles.UserRoleRequest
import com.example.frontend_android.model.roles.UserRoleResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRoleRepository@Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<UserRoleRequest, UserRoleResponse, API>()  {

    override suspend fun performAdd(
        api: API,
        data: UserRoleRequest
    ): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun performGet(api: API): Response<List<UserRoleResponse>> {
        return api.getAllUsersAndRoles()
    }

    override suspend fun performPatch(
        api: API,
        data: UserRoleRequest
        ): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun performGetById(
        api: API,
        targetId: Long
    ): Response<List<UserRoleResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun performGetByPairs(
        api: API,
        first: Long,
        second: Long
    ): Response<List<UserRoleResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun performRemove(
        api: API,
        toRemove: Long,
        fromTableId: Long
    ): Response<Unit> {
        TODO("Not yet implemented")
    }

}