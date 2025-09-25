package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.test.UserProjectAcceptRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProjectRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<UserProjectRequest, UserProjectResponse, API>() {


    override suspend fun performAdd(
        api: API,
        data: UserProjectRequest
    ): Response<Unit> {
        return apiInterface.addUserProject(projectId = data.projectId, userId = data.userId, req = data)
    }



    override suspend fun performGet(
        api: API,
    ): Response<List<UserProjectResponse>>{
        return api.getAllUsersProjects()

    }

    override suspend fun performPatch(
        api: API,
        data: UserProjectRequest
    ): Response<Unit> {
        return api.updateUserProject(data.projectId,data.userId, data )
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
        return apiInterface.getUserProject(
            projectId = first,
            userId = second,
        )
    }

    override suspend fun performRemove(
        api: API,
        toRemove: Long,
        fromTableId: Long
    ): Response<Unit> {
        return apiInterface.removeUserFromProject(fromTableId, toRemove)
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

    suspend fun getUserRequests(): Result<List<UserProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getRequestsForUser()
                val raw = response.body()
                Log.d("Invite", "REQUESTS RAW: $raw")
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Invite Error: ${response.code()} - ${response.message()}"))
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
                Log.d("Invite", "INVITES RAW: $raw")
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
                Log.d("Invite", "ACCEPTED RAW: $raw")
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Invite Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }




//    suspend fun getAllDataById(): Result<List<UserProjectResponse>> =
//        withContext(Dispatchers.IO) {
//            try {
//                val response = apiInterface.getAllUsersProjects()
//                if (response.isSuccessful) {
//                    Result.success(response.body().orEmpty())
//                } else {
//                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
//                }
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//
//
//    suspend fun getAllMembersById(projectId: Long?): Result<List<UserProjectResponse>> =
//        withContext(Dispatchers.IO) {
//            try {
//
//                val response = apiInterface.getMembers(projectId)
//                val rawBody = response.errorBody()?.string() ?: response.body()?.toString()
//                Log.d("GetMemberRaw", "Raw response: $rawBody and $projectId")
//                if (response.isSuccessful) {
//                    val body = response.body()
//                    Log.d("GetMemberRaw", "Mapped body: $body")
//                    Result.success(response.body().orEmpty())
//
//                } else {
//                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
//                }
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }




}


/**
 *     suspend fun getUserProjects(): Result<Unit>{
 *
 *         return TODO("Provide the return value")
 *     }
 *
 *     suspend fun add(name: String, description: String, genre: String): Result<String>
 *             = withContext(Dispatchers.IO) {
 *                 request = ProjectRequest(
 *             projectName = name,
 *             description = description,
 *             genre = genre
 *         )
 *         try {
 *             val resp = apiInterface.createProject(request)
 *             if (resp.isSuccessful) Result.success("HTTP ${resp.body()}")
 *             else Result.failure(Exception("HTTP  ${resp.code()} ${resp.message()}"))
 *         } catch (t: Throwable){
 *             Result.failure(t)
 *         }
 *     }
 */