package com.example.frontend_android.ui.userSettings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserRequestPatch
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.RoleRequest
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.model.roles.RoleResponse
import com.example.frontend_android.model.roles.RoleType
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.repository.UserRoleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach


/**
 * Håller data som ska skickas med hela tiden i alla delar av ett projekt (schema, visa användare etc)
 */
@HiltViewModel
class UserSettingsViewModel  @Inject constructor(
    private val uRepo: UserRepository,
    private val urRepo: UserRoleRepository
): ViewModel() {


    private lateinit var request: UserRequestPatch




    private val _user = MutableLiveData<UserResponse?>()
    val user: MutableLiveData<UserResponse?> = _user
    fun getUser() {
        viewModelScope.launch {
            Log.d("GetMemberUser", "TRYING", )
            val result = uRepo.returnUser()
            result.fold(
                onSuccess = { user ->
                    _user.postValue(user)
                    Log.d("GetMemberUser", "Member function works: Fetched:  ${user?.id}", )

                },
                onFailure = { e ->
                    Log.e("GetMemberUser", "aaaaMember function Error: Error loading userprojects", e)
                }
            )
        }
    }


    init {
        getUser()
        getRoles()
        getLoggedInRole()
    }

    private val _roles = MutableLiveData<List<RoleResponse>>()
    val roles: LiveData<List<RoleResponse>> get() = _roles
    fun getRoles() {
        Log.e("RegisterViewModel", "sending")
        viewModelScope.launch {
            val result = uRepo.getAllRoleTypes()
            result.fold(
                onSuccess = { list ->
                    _roles.postValue(list)
                },
                onFailure = { e ->
                    Log.e("RegisterViewModel", "Error roles:", e)
                }
            )
        }
    }

    private val _userRoles = MutableLiveData<List<String>>()
    val userRoles: LiveData<List<String>> = _userRoles
    fun getLoggedInRole() {
        viewModelScope.launch {
            val result = urRepo.getLoggedInRole()
            result.fold(
                onSuccess = { user ->
                    _userRoles.postValue(user)
                            },
                onFailure = { e ->
                    Log.e("getLoggedInRole", " function Error: Error loading userprojects", e)
                }
            )
        }
    }

    fun patchUser(firstName: String, lastName: String, password: String, roles: List<RoleRequest>){

        viewModelScope.launch {
            request = UserRequestPatch(
                firstName = firstName,
                lastName = lastName,
                password = password,
                roles = roles
            )
            Log.d("PATCH_USER", "firstName = $firstName")
            Log.d("PATCH_USER", "lastName = $lastName")
            Log.d("PATCH_USER", "password = $password")
            Log.d("PATCH_USER", "roles = ${roles.joinToString { it.role }}")

            val result = uRepo.updateUser(request)
            result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
        }
    }




}
