package com.example.frontend_android.ui.MyProject.admin
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.ui.MyProject.ProjectRequestViewModel
import com.example.frontend_android.ui.schedule.BookingsViewModel
import com.example.frontend_android.ui.schedule.ProjectUserFragmentArgs
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class UpAdminAddMemberFragment : Fragment(R.layout.fragment_projects){
    private val vm: ProjectRequestViewModel by viewModels()
    private val bvm: BookingsViewModel by activityViewModels()

    private lateinit var adapter: ProjectAddUsersAdapter

    private var showMembers: Boolean = false;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.projectsRecyclerView)
        val input = view.findViewById<EditText>(R.id.edit_query)
        val sendBtn = view.findViewById<MaterialButton>(R.id.searchBtn)


        val args: ProjectUserFragmentArgs by navArgs()
        val projectId = args.projectId

        adapter = ProjectAddUsersAdapter(
            accept = { u ->
                Log.d("requests", "requests project id: $u and user id: ")
                vm.sendInviteToUser(u, projectId)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        vm.users.observe(viewLifecycleOwner){ u ->
            if (u != null){
                adapter.submitList(u)
            }
        }

        sendBtn.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                vm.searchUsers("projectName", text)
                vm.users.value
            }else {
                vm.getNonMembers()
            }
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