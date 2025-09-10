package com.example.frontend_android.ui.schedule

import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<BookingRequest, BookingResponse, API>() {

    override suspend fun performAdd(
        api: API,
        data: BookingRequest,
        userId: Long?,
        targetId: Long?
    ): Response<Unit> {
        return apiInterface.createBooking(data)
    }

    override suspend fun performGet(
        api: API,
        userId: Long,
        targetId: Long?
    ): Response<BookingResponse> {
        TODO("Not yet implemented")
    }


    suspend fun getAllProjects(): Result<List<ProjectResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getAllProjects()
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun updateData(data: BookingRequest): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(id: BookingRequest): Result<Unit> {
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