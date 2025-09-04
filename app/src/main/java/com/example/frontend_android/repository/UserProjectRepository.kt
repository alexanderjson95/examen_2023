package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
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
        data: UserProjectRequest,
        userId: Long?,
        targetId: Long?
    ): Response<Unit> {
        return apiInterface.addUserProject(projectId = targetId, userId = userId, req = data)
    }

    override suspend fun performGet(
        api: API,
        userId: Long,
        targetId: Long?
    ): Response<UserProjectResponse> {
        return apiInterface.getUserProject(
            projectId = targetId,
            userId = userId,
        )
    }


    suspend fun getAllDataById(): Result<List<UserProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getAllUsersProjects()
                if (response.isSuccessful) {
                    val projects = response.body().orEmpty()

                    projects.forEach { p ->
                        Log.d("getAllDataById", "Project: ${p.projectName}, Role: ${p.role}")
                    }

                    return@withContext Result.success(projects)
                } else {
                    return@withContext Result.failure(
                        Exception("Error: ${response.code()} - ${response.message()}")
                    )
                }
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }



    override suspend fun updateData(data: UserProjectRequest): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(id: UserProjectRequest): Result<Unit> {
        TODO("Not yet implemented")
    }


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