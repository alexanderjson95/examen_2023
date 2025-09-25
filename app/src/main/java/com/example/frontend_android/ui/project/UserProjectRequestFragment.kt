package com.example.frontend_android.ui.project

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.ui.MyProject.invite.InviteUserAdapter
import com.example.frontend_android.ui.dashboard.UserProjectAdapter
import com.example.frontend_android.ui.schedule.BookingsViewModel
import com.example.frontend_android.ui.schedule.MemberAdapter
import com.example.frontend_android.ui.schedule.ProjectUserFragmentArgs
import com.example.frontend_android.ui.user.UsersViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
@AndroidEntryPoint
class UserProjectRequestFragment : Fragment(R.layout.fragment_my_project_adduser) {
    private val bvm: BookingsViewModel by activityViewModels()
    private val pvm: ProjectsViewModel by activityViewModels()

    private var userId: Long = 0L

    private var showMembers: Boolean = false;

    private lateinit var adapter: ProjectRequestAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.usersAddRecyclerView)
        val checkBox = view.findViewById<CheckBox>(R.id.showMembersCheck)



        adapter = ProjectRequestAdapter(
            add = { projectId ->
                Log.d("requests","requests project id: $projectId and user id: $userId")
                bvm.sendInvite(projectId, userId)
                bvm.getMember(projectId)
            },
//            remove = { projectId ->
//                Log.d("requests","requests project id: $projectId and user id: $userId")
//                bvm.removeUserRequest(projectId, userId)
//                bvm.getMember(projectId)
//            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter



        bvm.projects.observe(viewLifecycleOwner) { members ->
                adapter.submitList(members)
                Log.d("InviteUserFragment", "UserID from userprojects: ${members.map { it.userId }}")
        }


        checkBox.setOnCheckedChangeListener { _, isChecked ->
            showMembers = isChecked

        }
        val sendBtn = view.findViewById<MaterialButton>(R.id.user_searchBtn)

        sendBtn.setOnClickListener {
            pvm.sendRequestToProject(2L)
        }
    }

    private fun filterMembers(
        userList: List<UserRoleResponse>,
        memberList: List<UserProjectResponse>
    ): List<UserRoleResponse> {
        val membersIds = memberList.map { it.userId }.toHashSet()
        return if (showMembers) {
            userList.filter { it.userId in membersIds }
        } else {
            userList.filter { it.userId !in membersIds }
        }
    }
}
