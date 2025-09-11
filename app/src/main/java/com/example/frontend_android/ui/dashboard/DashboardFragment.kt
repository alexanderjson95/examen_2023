package com.example.frontend_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.databinding.FragmentDashboardBinding
import com.example.frontend_android.ui.login.LoginViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard){


    private val projectVM: DashboardViewModel by activityViewModels()

    private val adapter = UserProjectAdapter {
        findNavController().navigate(R.id.navigation_myProject)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.projectsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        // Hämtar användarens projekt
        projectVM.getAllUserProjects()


        projectVM.projects.observe(viewLifecycleOwner) { projects ->
            adapter.submitList(projects)
        }


    }

}