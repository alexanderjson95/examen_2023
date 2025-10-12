package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequestPatch
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.google.gson.Gson
//import com.example.frontend_android.test.UserProjectAcceptRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProjectRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<UserProjectRequest, UserProjectResponse, UserProjectRequestPatch, API>() {


    override suspend fun performAdd(
        api: API,
        data: UserProjectRequest
    ): Response<Unit> {
        return apiInterface.addUserProject(
            projectId = data.projectId,
            userId = data.userId,
            req = data
        )
    }


    override suspend fun performGet(
        api: API,
    ): Response<List<UserProjectResponse>> {
        return api.getAllUsersProjects()

    }

    override suspend fun performPatch(
        api: API,
        data: UserProjectRequestPatch
    ): Response<Unit> {
        return api.updateUserProject(data.projectId, data.userId, data)
    }

    override suspend fun performGetById(
        api: API,
        targetId: Long
    ): Response<List<UserProjectResponse>> {
        return api.getMembers(targetId)
    }

    override suspend fun performGetByPairs(
        api: API,
        first: Long,
        second: Long
    ): Response<List<UserProjectResponse>> {
            TODO()
    }

    override suspend fun performRemove(
        api: API,
        toRemove: Long,
    ): Response<Unit> {
        TODO()
    }

    override suspend fun performRemovePair(
        api: API,
        toRemove: Long,
        fromTable: Long,
    ): Response<Unit> {
        return apiInterface.removeUserFromProject(
            projectId = toRemove,
            userId = fromTable
        )
    }

    suspend fun isAdmin(projectId: Long): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext apiInterface.isUserAdmin(projectId)
        }

    suspend fun getProjectInvites(projectId: Long): Result<List<UserProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getInvitesFromProject(projectId)
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getProjectRequests(projectId: Long): Result<List<UserProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getRequestsFromProject(projectId)
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getUserProject(projectId:Long, userId:Long): Result<UserProjectResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getUserProject(projectId, userId)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        Result.success(body)
                    } ?: Result.failure(Exception("Ingen data hämtad!"))                } else {
                    Result.failure(Exception("Fetch  Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getUserInvites(): Result<List<UserProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getInvitesFromUser()
                val raw = response.body()
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Invite Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getAcceptedProjectsUser(): Result<List<UserProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getAcceptedUserProjects()
                val raw = response.body()
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Invite Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun searchUserProjects(
        query: String,
        projectId: Long
    ): Result<List<UserProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.searchUserProjects(query, projectId)
                val rawBody = response.errorBody()?.string() ?: Gson().toJson(response.body())
                Log.d("GetMemberRaw", "Raw response: $rawBody and $query")

                if (response.isSuccessful) {

                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}