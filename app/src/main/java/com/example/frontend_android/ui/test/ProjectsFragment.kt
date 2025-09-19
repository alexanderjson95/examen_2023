package com.example.frontend_android.ui.test

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.ui.project.ProjectAdapter
import com.example.frontend_android.ui.dashboard.UserProjectAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class ProjectsFragment : Fragment(R.layout.fragment_projects){


    private val projectVM: ProjectsViewModel by activityViewModels()
    @Inject
    lateinit var adapter: ProjectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.projectsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        projectVM.getAllProjects()
        observeViewModel()
        setupSearch()
    }

    private fun setupSearch(){
        val queryField = view?.findViewById<EditText>(R.id.edit_query)
        view?.findViewById<Button>(R.id.searchBtn)?.setOnClickListener{
            val query = queryField?.text.toString()
            if (query.isNotEmpty()){
                projectVM.getAllProjects()
            }
        }
    }
    private fun observeViewModel(){
        projectVM.projects.observe(viewLifecycleOwner){
            projects ->
            if (projects != null){
                adapter.submitList(projects)
            }
        }
    }

}