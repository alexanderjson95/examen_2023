package com.example.frontend_android.ui.conversations.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.ui.conversations.MessageViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class ShowContactsFragment : Fragment(R.layout.show_message) {

    private val vm: MessageViewModel by viewModels()



    private val adapter by lazy {
        ShowContactsAdapter(
            openMessage = { recipientId, fName, lName ->
                val action =
                    ShowContactsFragmentDirections.dashToWriteMessage(recipientId, fName, lName)
                findNavController().navigate(action)
            })
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.messagesRecyclerView)
         recyclerView.layoutManager = LinearLayoutManager(requireContext())
         recyclerView.adapter = adapter
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        val noProjectCard = view.findViewById<MaterialCardView>(R.id.no_projects)

        vm.getUserMessages()


        viewLifecycleOwner.lifecycleScope.launch {
            vm.contacts.collect { m ->
                if (m.isNotEmpty()) {
                    adapter.submitList(m)
                    noProjectCard.visibility = if (m.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }

        }


        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnCreateProjects  -> {
                        val actionDialog = ShowContactsFragmentDirections
                            .dashToAddConvo()
                        findNavController().navigate(actionDialog)
                    }
                }
            }
        }
    }
}



