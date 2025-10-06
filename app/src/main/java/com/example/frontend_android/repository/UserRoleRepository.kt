package com.example.frontend_android.repository

import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.roles.RoleResponse
import com.example.frontend_android.model.roles.UserRoleRequest
import com.example.frontend_android.model.roles.UserRoleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRoleRepository@Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<UserRoleRequest, UserRoleResponse,UserRoleRequest, API>()  {

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
    ): Response<Unit> {
        TODO("Not yet implemented")
    }


    suspend fun getLoggedInRole(): Result<List<String>>
            = withContext(Dispatchers.IO){
        try {
            val response = apiInterface.getLoggedInRole()
            val body = response.body()
            if (response.isSuccessful && body != null)
                Result.success(body)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

}