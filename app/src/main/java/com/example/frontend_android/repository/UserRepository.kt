package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserRequestPatch
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.RoleResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<UserRequest, UserResponse,UserRequestPatch, API>(){
    private lateinit var userRequest: UserRequest


    override suspend fun performAdd(
        api: API,
        data: UserRequest,
    ): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun performGet(
        api: API,
    ): Response<List<UserResponse>> {
        return api.getAllUsers()
    }

    override suspend fun performPatch(
        api: API,
        data: UserRequestPatch,
    ): Response<Unit> {
        return api.updateUser(data)
    }

    override suspend fun performGetById(
        api: API,
        targetId: Long,
    ): Response<List<UserResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun performGetByPairs(
        api: API,
        first: Long,
        second: Long,
    ): Response<List<UserResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun performRemove(
        api: API,
        toRemove: Long,
    ): Response<Unit> {
        return api.deleteUser(toRemove)
    }

    override suspend fun performRemovePair(
        api: API,
        toRemove: Long,
        fromTable: Long,
    ): Response<Unit> {
        TODO("Not yet implemented")
    }


    suspend fun removeSelf(userId: Long): Result<Unit> =

        withContext(Dispatchers.IO) {
            Log.d("Remove Run Repository", "Running remove with id:")

            try {
                val response = apiInterface.deleteUser(userId)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Removal error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun updateUser(userRequestPatch: UserRequestPatch): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.updateUser(userRequestPatch)
                Log.d("role","${response.body()}")
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Role error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getAllRoleTypes(): Result<List<RoleResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getAllRoles()
                Log.d("role","${response.body()}")
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Role error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    suspend fun getUserRoles(userId: Long?): Result<List<RoleResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getMemberRoles(userId)
                Log.d("role","${response.body()}")
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Role error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getUserRoless(userId: Long): List<String> {
        val roles = apiInterface.getUserRoles(userId)
        println("Fetched roles: $roles")
        return roles
    }

    suspend fun returnUser(): Result<UserResponse?> = withContext(Dispatchers.IO) {
        try {
            val response = apiInterface.returnUser()

            if (response.isSuccessful) {

                Result.success(response.body())
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchUsers(query: String, value: String): Result<List<UserResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.searchUsers(query, value)
                if (response.isSuccessful) {

                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getAll(): Result<List<UserResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getAllUsers()
                if (response.isSuccessful) {
                    Log.d("All users", "users: ${response.body()}")
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception(" getAll Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }


    suspend fun registerUser(request: UserRequest): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.registerUser(request)
                val gson = Gson()
                Log.d("API", "Outgoing JSON: ${gson.toJson(request)}")
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Log.e("ADD ERROR", "HTTP ${response.code()} ${response.message()} - ${response.errorBody()?.string()}")
                    Result.failure(Exception("ADD ERROR ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
