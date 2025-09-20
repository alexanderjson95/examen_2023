package com.example.frontend_android.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.ui.dashboard.DashboardFragmentDirections
import com.example.frontend_android.ui.dashboard.UserProjectAdapter
import com.example.frontend_android.ui.user.UsersAdapter
import com.example.frontend_android.ui.user.UsersViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class ProjectUserFragment : Fragment(R.layout.fragment_myprojectusers){
    private val vm: UsersViewModel by activityViewModels()
    private val bookingVM: BookingsViewModel by activityViewModels()
    private var projectId: Long? = null

    private var memberId: Long? = null

    private var isAdmin: Boolean = false

    private var userId: Long = 0L


    private val args: ProjectUserFragmentArgs by navArgs()
    private lateinit var adapter: MemberAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectId = args.projectId
        userId = args.userId

        val recyclerView = view.findViewById<RecyclerView>(R.id.membersRecyclerView)


        adapter = MemberAdapter { memberId ->

            if (isAdmin && memberId != userId) {
                Toast.makeText(requireContext(), "Member: ${memberId} is being kicked!!!!", Toast.LENGTH_SHORT).show()
            }

            else if (memberId == userId) {
                Toast.makeText(requireContext(), "Member: ${memberId} leaving!!!!!!!", Toast.LENGTH_SHORT).show()
            }

            else
            {
                Toast.makeText(requireContext(), "Member: ${memberId} is NOT being kicked!!!!", Toast.LENGTH_SHORT).show()
            }


        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        projectId?.let { bookingVM.getMember(projectId) }

        bookingVM.getUser()
        bookingVM.user.observe(viewLifecycleOwner) { u ->
            userId = u?.id ?: 0
            Toast.makeText(requireContext(), "userid : $userId", Toast.LENGTH_SHORT).show()
            bookingVM.getRoless(userId)
        }

        bookingVM.roless.observe(viewLifecycleOwner) { roles ->
            roles.forEach { role ->
                println("ROLES: $role")
            }

        }

        bookingVM.members.observe(viewLifecycleOwner) { members ->
            adapter.submitList(members)
            val adminUser = userId
            isAdmin = members.any { it.userId == adminUser && it.isAdmin == true }
            Toast.makeText(requireContext(), "Member: $isAdmin", Toast.LENGTH_SHORT).show()
        }
    }
        override fun onDestroyView() {
            super.onDestroyView()
            adapter.submitList(emptyList())
            bookingVM.clearAll()
        }
}



/*
            for (m in members) {
                if (m.userId == userId && m.isAdmin == true) {
                    Toast.makeText(requireContext(), "Member: ${m.userId} is a admin!", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(requireContext(), "Member: ${m.userId} is not an admin!", Toast.LENGTH_SHORT).show()
                }
            }

            adapter.returnUsername = object : MemberAdapter.ReturnUsername {
                override fun onFetchAttempt(username: String?) {
                    if ()
                }
            }
 */
