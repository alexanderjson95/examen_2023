package com.example.frontend_android.ui.messages

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.MyProject.invite.InviteUserAdapter
import com.example.frontend_android.ui.dashboard.DashboardFragmentDirections
import com.example.frontend_android.ui.schedule.BookingsViewModel
import com.example.frontend_android.ui.schedule.ProjectUserFragmentArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.map
import kotlin.collections.mapNotNull
import kotlin.getValue

@AndroidEntryPoint
class ShowMessagesFragment : Fragment(R.layout.show_message) {

    private val vm: MessageViewModel by activityViewModels()

    private val adapter by lazy { ShowMessagesAdapter(
        openMessage = { recipientId, fName, lName ->
            val action = ShowMessagesFragmentDirections.dashToWriteMessage(recipientId,fName,lName)
            findNavController().navigate(action) })
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.messagesRecyclerView)
         recyclerView.layoutManager = LinearLayoutManager(requireContext())
         recyclerView.adapter = adapter
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        val noProjectCard = view.findViewById<MaterialCardView>(R.id.no_projects)

        vm.getUserMessages()
        vm.contacts.observe(viewLifecycleOwner){
                messages ->
                    if (messages != null){
                    adapter.submitList(messages)
                        noProjectCard.visibility = if (messages.isEmpty()) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                }
            }

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnCreateProjects  -> {
                        val actionDialog = ShowMessagesFragmentDirections
                            .dashToAddConvo()
                        findNavController().navigate(actionDialog)
                    }
                }
            }
        }
    }
}



