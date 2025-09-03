package com.example.frontend_android.repository

import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
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