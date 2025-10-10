package com.example.frontend_android.ui.conversations.contacts

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.frontend_android.ui.conversations.MessageViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddContactFragment : Fragment(R.layout.fragment_add_convo)
{
    private val vm: MessageViewModel by activityViewModels()
    private lateinit var adapter: AddContactAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.usersAddRecyclerView)
        val input = view.findViewById<EditText>(R.id.user_edit_query)
        val searchBtn = view.findViewById<MaterialButton>(R.id.searchBtn)
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)

        adapter = AddContactAdapter(
            openMessage = { id,fn,ln ->
                val action = AddContactFragmentDirections.dashToWriteMessage(id,fn,ln)
                findNavController().navigate(action)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            vm.users.collect { m ->
                if (m.isNotEmpty()) {
                    adapter.submitList(m){recyclerView.scrollToPosition(adapter.itemCount - 1)}
                }
            }

        }

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnMyConvos -> {
                        val actionDialog = AddContactFragmentDirections
                            .dashToShowMessages()
                        findNavController().navigate(actionDialog)
                    }
                }
            }
        }



        searchBtn.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                // sök users här
                vm.searchUsers("firstname", text)
            } else {
                Log.d("Messages", "MESSAGE: TYPE IN TEXT!!!!")
            }
        } }


}

