package com.example.frontend_android.ui.dashboard.invites.projectInvites
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.frontend_android.ui.dashboard.DashboardFragmentDirections
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.materialswitch.MaterialSwitch
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProjectInvites : Fragment(R.layout.fragment_my_project_requests)
{
    private val vm: UserRequestViewmodel by viewModels()
    private lateinit var adapter: UserProjectRequestStatusAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.projectRequestRecycler)
        adapter = UserProjectRequestStatusAdapter(
            remove = { p, u -> vm.remove(p) }, //
            accept = { p, u -> vm.acceptInvite(p, u) }
        )

        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val no_invite_card = view.findViewById<MaterialCardView>(R.id.no_invite_card)
        no_invite_card.visibility = View.GONE
//här



        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                vm.userprojects.collect { i ->
                    adapter.submitList(i)
                    no_invite_card.visibility =
                        if (i.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }

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
                        val action = ProjectInvitesDirections
                            .projectInvToBookingInv()
                        findNavController().navigate(action)
                    }
                }
            }
        }

    }
}






//toggleFilter.addOnButtonCheckedListener { _, checkedId, isChecked ->
//            if (isChecked) {
//                when (checkedId) {
//                    R.id.showInvites -> {
////                        adapter.submitList(vm.requests.value ?: emptyList())
//                        showRequests.setBackgroundColor("#577590".toColorInt())
//                        showInvites.setBackgroundColor("#F9F8F8".toColorInt())
//                        showRequests.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//                        showInvites.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//
//                    }
//                    R.id.showRequests  -> {
////                        adapter.submitList(vm.invites.value ?: emptyList())
//                        showInvites.setBackgroundColor("#577590".toColorInt())
//                        showRequests.setBackgroundColor("#F9F8F8".toColorInt())
//                        showRequests.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//                        showInvites.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//                    }
//                }
//            }
//        }


//        vm.requests.observe(viewLifecycleOwner) { u ->
//            adapter.submitList(u ?: emptyList())
//            no_invite_card.visibility = if (u.isNullOrEmpty()) {
//                View.VISIBLE
//            } else {
//                View.GONE
//            }
//        }
//
//        vm.invites.observe(viewLifecycleOwner) { u ->
//            adapter.submitList(u ?: emptyList())
//            no_invite_card.visibility = if (u.isNullOrEmpty()) {
//                View.VISIBLE
//            } else {
//                View.GONE
//            }
//        }