package com.example.frontend_android.ui.userProject.projectInvites
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.ui.dashboard.DashboardFragmentDirections
import com.example.frontend_android.ui.dashboard.invites.projectInvites.ProjectInvitesDirections
import com.example.frontend_android.ui.dashboard.invites.projectInvites.UserProjectRequestStatusAdapter
import com.example.frontend_android.ui.dashboard.invites.projectInvites.UserRequestViewmodel
import com.example.frontend_android.ui.userProject.projectDashboard.MyProjectFragmentArgs
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.materialswitch.MaterialSwitch
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class UserProjectInvites : Fragment(R.layout.fragment_my_project_requests)
{
    private val vm: UserProjectRequestViewmodel by viewModels()
    private lateinit var adapter: ProjectUserRequestStatusAdapter
    private val args: MyProjectFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.projectRequestRecycler)
        adapter = ProjectUserRequestStatusAdapter(
            remove = { p, u -> vm.remove(u) },
            accept = { p, u -> vm.acceptRequest(p, u) }
        )
        val no_invite_card = view.findViewById<MaterialCardView>(R.id.no_invite_card)

        val projectId = args.projectId

        vm.getUserProjects(projectId)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.userprojects.collect { list ->
                    adapter.submitList(list)
                    no_invite_card.isVisible = list.isEmpty()
                }
            }
        }


        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val btnBookingNav = view.findViewById<MaterialButton>(R.id.btnBookingNav)

        val toggleFilter = view.findViewById<ChipGroup>(R.id.filterToggleGroup)
        toggleFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val all = vm.userprojects.value
            val filter = all.filter  { p ->
                if (checkedIds.isEmpty()){
                    adapter.submitList(all)
                    return@setOnCheckedStateChangeListener
                }
                when(p.requestType){
                    "ACCEPTED" -> R.id.fAccepted in checkedIds
                    "INVITE" -> R.id.fInvite in checkedIds
                    "DECLINED" -> R.id.fDeclined in checkedIds
                    "REQUEST" -> R.id.fRequest in checkedIds
                    else -> false
                }
            }
            adapter.submitList(filter)
        }




        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnBookingNav  -> {
                        val action = UserProjectInvitesDirections.userprojectToBookingproject()
                        findNavController().navigate(action)
                    }


                }
            }
        }
    }
    private fun showCard(): Boolean {
        val inv= vm.invites.value
        val avail = vm.requests.value
        return inv.isNullOrEmpty() && avail.isNullOrEmpty()
    }

}