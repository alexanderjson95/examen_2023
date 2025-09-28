package com.example.frontend_android.ui.MyProject.admin

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.test.UserProjectRequestStatusAdapter
import com.example.frontend_android.ui.MyProject.MyProjectFragmentArgs
import com.example.frontend_android.ui.MyProject.ProjectRequestViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MyProjectInviteRequestsFragment : Fragment(R.layout.fragment_my_project_requests)
{
    private val vm: ProjectRequestViewModel by viewModels()
    private lateinit var adapter: ProjectInvitesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.projectRequestRecycler)
        val args: MyProjectFragmentArgs by navArgs()
        val projectId = args.projectId

        adapter = ProjectInvitesAdapter(
            remove = { p, u -> vm.remove(u, p) },
            accept = { p, u -> vm.acceptInvite(p, u) }
        )

        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        vm.getRequests(projectId)
        vm.getInvites(projectId)

        vm.requests.observe(viewLifecycleOwner){ u ->
            if (!u.isNullOrEmpty()) {
                adapter.submitList(u)
            }
        }

        vm.invites.observe(viewLifecycleOwner){ u ->
            if (!u.isNullOrEmpty()) {
                adapter.submitList(u)
            }
        }

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnRequests -> adapter.submitList(vm.requests.value ?: emptyList())
                    R.id.btnInvites  -> adapter.submitList(vm.invites.value ?: emptyList())
                }
            }
        }
    }
}