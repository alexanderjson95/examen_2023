package com.example.frontend_android.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard){


    private val projectVM: DashboardViewModel by activityViewModels()

    private val adapter = UserProjectAdapter { projectId ->
        val action = DashboardFragmentDirections
            .dashToMyProject(projectId)
        findNavController().navigate(action)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.projectsRecyclerView)
        val addProject = view.findViewById<MaterialButton>(R.id.create_project_btn)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        parentFragmentManager.setFragmentResultListener("addProjectRequest", viewLifecycleOwner) { _, bundle ->
            val name = bundle.getString("projectName")
            val desc = bundle.getString("projectDescription")
            projectVM.addProject(name,desc)
        }


        // Hämtar användarens projekt
        projectVM.getAllUserProjects()


        projectVM.projects.observe(viewLifecycleOwner) { projects ->
            adapter.submitList(projects)
        }



        addProject.setOnClickListener {
            val actionDialog = DashboardFragmentDirections
                .dashToProjectDialog()
            findNavController().navigate(actionDialog)
        }

    }

}