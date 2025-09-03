package com.example.frontend_android.repository

import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.api.SharedPrefsUtils
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Users.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<ProjectRequest, API>() {

    override suspend fun performAdd(
        api: API,
        data: ProjectRequest
    ): Response<Unit> {
        return apiInterface.createProject(data)
    }

    override suspend fun getData(data: ProjectRequest): Result<ProjectRequest> {
        TODO("Not yet implemented")
    }

    override suspend fun updateData(data: ProjectRequest): Result<ProjectRequest> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(data: ProjectRequest): Result<ProjectRequest> {
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