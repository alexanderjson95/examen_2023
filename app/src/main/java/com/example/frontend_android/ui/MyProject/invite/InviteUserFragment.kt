package com.example.frontend_android.ui.MyProject.invite

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
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.schedule.BookingsViewModel
import com.example.frontend_android.ui.schedule.ProjectUserFragmentArgs
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class InviteUserFragment : Fragment(R.layout.fragment_my_project_adduser){
    private val bvm: BookingsViewModel by activityViewModels()

    private var projectId: Long = 0L
    private var userId: Long = 0L

    private var userList: List<UserResponse> = emptyList()
    private var memberList: List<UserProjectResponse> = emptyList()
    private val args: ProjectUserFragmentArgs by navArgs()

    private var showMembers: Boolean = false;

    private lateinit var adapter: InviteUserAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.usersAddRecyclerView)
        val checkBox = view.findViewById<CheckBox>(R.id.showMembersCheck)

        projectId = args.projectId
        observeViewModel()

        adapter = InviteUserAdapter ( addUser = {userId ->
            bvm.sendInvite(projectId, userId)
            bvm.getMember(projectId)
            }
        )


        checkBox.setOnCheckedChangeListener { _, isChecked ->
            showMembers = isChecked
            val users = bvm.users.value
            val members = bvm.members.value
            if (users != null && members != null) {
                val memberIds = members.mapNotNull { it.userId }.toSet()
                adapter.updateId(memberIds)
                adapter.submitList(filterMembers(users,members))
            }
        }


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val input = view.findViewById<EditText>(R.id.user_edit_query)
        val sendBtn = view.findViewById<MaterialButton>(R.id.user_searchBtn)

        sendBtn.setOnClickListener {
            Log.d("Users", "No users found")
            val text = input.text.toString()
            bvm.getMember(projectId)
            if (text.isNotEmpty()) {
                bvm.searchUsers("username", text)
            } else {
                bvm.getUsers()
            }
        }

        //todo error fixa: så den upptaderas även i in


    }
    private fun observeViewModel(){
        bvm.users.observe(viewLifecycleOwner){
                users ->
                    val members = bvm.members.value
                    if (users != null && members != null){
                        val memberIds = members.mapNotNull { it.userId }.toSet()
                        adapter.updateId(memberIds)
                    adapter.submitList(filterMembers(users,members))
                        Log.d("InviteUserFragment", "Fetched users: ${users.map { it.id }}")
                }
            }
        bvm.members.observe(viewLifecycleOwner) { members ->
                val users = bvm.users.value
                if (users != null && members != null){
                    val memberIds = members.mapNotNull { it.userId }.toSet()
                    adapter.updateId(memberIds)
                    adapter.submitList(filterMembers(users,members))
                    Log.d("InviteUserFragment", "Fetched users: ${users.map { it.id }}")
            }
        }
    }

    // Vi tar ID från members och konverterar till HashSet (som inte tillåter duplicates), filtrerar därmed ut alla lika ID.
    // snabbare än att söka linjärt. showMembers är vår toggle till vilken lista som vi ska visa.
    private fun filterMembers(userList: List<UserResponse>, memberList: List<UserProjectResponse>): List<UserResponse> {
        val membersIds = memberList.mapNotNull { it.userId }.toHashSet()
        return if (showMembers) {
            userList.filter { it.id in membersIds }
        } else {
            userList.filter { it.id !in membersIds }
        }
    }

}


