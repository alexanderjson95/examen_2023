package com.example.frontend_android.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
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
    private val args: ProjectUserFragmentArgs by navArgs()
    private lateinit var adapter: MemberAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectId = args.projectId
        val recyclerView = view.findViewById<RecyclerView>(R.id.membersRecyclerView)
        adapter = MemberAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        bookingVM.members.observe(viewLifecycleOwner) { members ->
            adapter.submitList(members)
        }
        projectId?.let { bookingVM.getMember(projectId) }
    }
     }

