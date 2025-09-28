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
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
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
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        val noProjectCard = view.findViewById<MaterialCardView>(R.id.no_projects)


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        parentFragmentManager.setFragmentResultListener("addProjectRequest", viewLifecycleOwner) { _, bundle ->
            val name = bundle.getString("projectName")
            val desc = bundle.getString("projectDescription")
            projectVM.addProject(name,desc)
        }
        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnRequests -> {
                        val actionDialog = DashboardFragmentDirections
                            .dashToProjectDialog()
                        findNavController().navigate(actionDialog)
                    }
                    R.id.btnCreateProjects  -> {
                        val actionDialog = DashboardFragmentDirections
                            .dashToProjectDialog()
                        findNavController().navigate(actionDialog)
                    }
                }
            }
        }

        // Hämtar användarens projekt
        projectVM.getAllUserProjects()


        projectVM.projects.observe(viewLifecycleOwner) { projects ->
            adapter.submitList(projects)
            noProjectCard.visibility = if (projects.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }



//        addProject.setOnClickListener {
//            val actionDialog = DashboardFragmentDirections
//                .dashToProjectDialog()
//            findNavController().navigate(actionDialog)
//        }

    }

}