package com.example.frontend_android.test

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.messages.AddConvoAdapter
import com.example.frontend_android.ui.messages.MessageViewModel
import com.example.frontend_android.ui.messages.ShowMessagesAdapter
import com.example.frontend_android.ui.messages.ShowMessagesFragmentDirections
import com.example.frontend_android.ui.messages.WriteMessageFragmentArgs
import kotlin.collections.map
import kotlin.collections.mapNotNull

@AndroidEntryPoint
class AddConvoFragment : Fragment(R.layout.fragment_add_convo)
{
    private val vm: MessageViewModel by activityViewModels()

    private var recipientId: Long = 0L

    private var showContacts: Boolean = false;

    private lateinit var adapter: AddConvoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.usersAddRecyclerView)
        val input = view.findViewById<EditText>(R.id.user_edit_query)
        val searchBtn = view.findViewById<MaterialButton>(R.id.searchBtn)
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)

        adapter = AddConvoAdapter(
            openMessage = { id,fn,ln ->
                val action = AddConvoFragmentDirections.dashToWriteMessage(id,fn,ln)
                findNavController().navigate(action)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        vm.users.observe(viewLifecycleOwner) { u ->
                adapter.submitList(u)
        }

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnMyConvos -> {
                        val actionDialog = AddConvoFragmentDirections
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


    private fun filterMembers(
        userList: List<UserResponse>,
        memberList: List<UserProjectResponse>
    ): List<UserResponse> {
        val membersIds = memberList.mapNotNull { it.userId }.toHashSet()
        return if (showContacts) {
            userList.filter { it.id in membersIds }
        } else {
            userList.filter { it.id !in membersIds }
        }
    }
}

