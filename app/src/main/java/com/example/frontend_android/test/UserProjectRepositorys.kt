//package com.example.frontend_android.test
//
//import android.util.Log
//import com.example.frontend_android.api.API
//import com.example.frontend_android.api.RepositoryAbstract
//import com.example.frontend_android.model.Projects.UserProjectRequest
//import com.example.frontend_android.model.Projects.UserProjectResponse
//import com.example.frontend_android.model.Users.UserResponse
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import retrofit2.Response
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class UserProjectRepositorys @Inject constructor(
//    override val apiInterface: API,
//) : RepositoryAbstract<UserProjectRequest, UserProjectResponse, API>() {
//
//
//    override suspend fun performAdd(
//        api: API,
//        data: UserProjectRequest,
//
//    ): Response<Unit> {
//        return apiInterface.addUserProject(projectId = targetId, userId = userId, req = data)
//    }
//
//    override suspend fun performGet(
//        api: API,
//        userId: Long?,
//        targetId: Long?
//    ): Response<List<UserProjectResponse>>{
//        return apiInterface.getUserProject(
//            projectId = targetId,
//            userId = userId,
//        )
//    }
//
//    override suspend fun performPatch(
//        api: API,
//        data: UserProjectRequest,
//        bookingId: Long
//    ): Response<Unit> {
//        TODO("Not yet implemented")
//    }
//
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
//
//    override suspend fun deleteData(id: UserProjectRequest): Result<Unit> {
//        TODO("Not yet implemented")
//    }
//
//    suspend fun searchUsers(query: String, value: String): Result<List<UserResponse>> =
//        withContext(Dispatchers.IO) {
//            try {
//                val response = apiInterface.searchUsers(query, value)
//                if (response.isSuccessful) {
//
//                    Result.success(response.body().orEmpty())
//                } else {
//                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
//                }
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//
//}


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