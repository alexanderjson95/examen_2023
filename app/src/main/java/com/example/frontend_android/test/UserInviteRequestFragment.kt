package com.example.frontend_android.test

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.ui.schedule.UserProjectAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.graphics.toColorInt

@AndroidEntryPoint
class UserInviteRequestFragment : Fragment(R.layout.fragment_my_project_requests)
{
    private val vm: UserRequestViewmodel by viewModels()
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


        vm.getRequests()
        vm.getInvites()


        val no_invite_card = view.findViewById<MaterialCardView>(R.id.no_invite_card)

        vm.requests.observe(viewLifecycleOwner) { u ->
            adapter.submitList(u ?: emptyList())
            no_invite_card.visibility = if (u.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        vm.invites.observe(viewLifecycleOwner) { u ->
            adapter.submitList(u ?: emptyList())
            no_invite_card.visibility = if (u.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        val btnRequests = view.findViewById<MaterialButton>(R.id.btnRequests)
        val btnInvites = view.findViewById<MaterialButton>(R.id.btnInvites)

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnRequests -> {
                        adapter.submitList(vm.requests.value ?: emptyList())
                        btnRequests.setBackgroundColor("#577590".toColorInt())
                        btnInvites.setBackgroundColor("#F9F8F8".toColorInt())
                        btnRequests.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        btnInvites.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                    }
                    R.id.btnInvites  -> {
                        adapter.submitList(vm.invites.value ?: emptyList())
                        btnInvites.setBackgroundColor("#577590".toColorInt())
                        btnRequests.setBackgroundColor("#F9F8F8".toColorInt())
                        btnRequests.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                        btnInvites.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))                    }
                }
            }
        }


    }

}