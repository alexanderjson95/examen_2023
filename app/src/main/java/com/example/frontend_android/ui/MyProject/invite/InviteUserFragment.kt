package com.example.frontend_android.ui.MyProject.invite

import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.example.frontend_android.ui.schedule.MemberAdapter
import com.example.frontend_android.ui.schedule.ProjectUserFragmentArgs
import com.example.frontend_android.ui.user.UsersAdapter
import com.example.frontend_android.ui.user.UsersViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class InviteUserFragment : Fragment(R.layout.fragment_my_project_adduser){
    private val vm: UsersViewModel by activityViewModels()
    private var projectId: Long? = null
    private var userId: Long = 0L

    private val args: ProjectUserFragmentArgs by navArgs()

    private var usersList: List<UserResponse> = emptyList()

    private lateinit var adapter: InviteUserAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.usersRecyclerView)

        projectId = args.projectId
        userId = args.userId

        adapter = InviteUserAdapter { userId ->
            Toast.makeText(requireContext(), "Member: ${userId} is being kicked!!!!", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter





        val input = view.findViewById<EditText>(R.id.user_edit_query)
        val sendBtn = view.findViewById<MaterialButton>(R.id.user_searchBtn)


        sendBtn.setOnClickListener {
            Log.d("Users", "No users found")
            observeViewModel()
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                vm.searchUsers("username", text)
            } else {
                vm.getUsers()
            }
        }
    }

    private fun observeViewModel(){
        vm.users.observe(viewLifecycleOwner){
                users ->
            if (users != null){
               adapter.submitList(users)
            }
        }
    }
}