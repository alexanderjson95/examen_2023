package com.example.frontend_android.test

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.ui.MyProject.ProjectRequestViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpRequestsInvitesFragment : Fragment(R.layout.fragment_my_project_requests)
{
    private val vm: ProjectRequestViewModel by viewModels()
    private lateinit var adapter: UserProjectRequestStatusAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.projectRequestRecycler)
        adapter = UserProjectRequestStatusAdapter(
            remove = {p,u -> vm.remove(u,p)},
            accept = {p,u -> vm.acceptInvite(p,u)}
        )

        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        vm.getRequests()
//        vm.getInvites()

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