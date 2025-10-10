package com.example.frontend_android.ui.projects

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ProjectsFragment : Fragment(R.layout.fragment_projects){

    private var userId: Long = 0L

    private val projectVM: ProjectsViewModel by viewModels()

    private lateinit var adapter: ProjectAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.projectsRecyclerView)
        val input = view.findViewById<EditText>(R.id.edit_query)
        val sendBtn = view.findViewById<MaterialButton>(R.id.searchBtn)

        projectVM.getUser()
        projectVM.user.observe(viewLifecycleOwner) {user ->
            userId = user?.id ?: 0L
        }
        adapter = ProjectAdapter(
            add = { projectId ->
                projectVM.sendInvite(projectId, userId)
                projectVM.getMember(projectId)
            }
        )



        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        projectVM.projects.observe(viewLifecycleOwner){
                projects ->
            if (projects != null){
                adapter.submitList(projects)
            }
        }


        projectVM.userProjects.observe(viewLifecycleOwner){
                up -> if (up != null){
               val requestedProjects =  up.map { it.projectName }
                Log.d("requestedProjects", "User has requested to join: $requestedProjects")
            }
        }

        sendBtn.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                projectVM.searchProjects("projectName", text)
                projectVM.projects.value
            }else {
                projectVM.getAllProjects()
            }
        }
    }
}