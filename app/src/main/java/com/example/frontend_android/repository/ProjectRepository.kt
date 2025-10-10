package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectRequestPatch
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<ProjectRequest, ProjectResponse, ProjectRequestPatch,API>() {

    override suspend fun performAdd(
        api: API,
        data: ProjectRequest
    ): Response<Unit> {
        return apiInterface.createProject(data)
    }


    override suspend fun performGet(
        api: API,
    ): Response<List<ProjectResponse>>{
        return api.getProjectsUserIsNotIn()
    }

    override suspend fun performPatch(
        api: API,
        data: ProjectRequestPatch
    ): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun performGetById(
        api: API,
        targetId: Long
    ): Response<List<ProjectResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun performGetByPairs(
        api: API,
        first: Long,
        second: Long
    ): Response<List<ProjectResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun performRemove(
        api: API,
        toRemove: Long
    ): Response<Unit> {
        TODO()
    }

    override suspend fun performRemovePair(
        api: API,
        toRemove: Long,
        fromTable: Long,
    ): Response<Unit> {
        return api.deleteProject(toRemove,fromTable)
    }

    suspend fun removeProject(): Result<List<ProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getProjectsUserIsNotIn()
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun searchProjects(query: String, value: String): Result<List<ProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.searchProjects(query, value)
                val rawBody = response.errorBody()?.string() ?: Gson().toJson(response.body())

                if (response.isSuccessful) {

                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    suspend fun getAllUnaddedProjects(): Result<List<ProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getProjectsUserIsNotIn()
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