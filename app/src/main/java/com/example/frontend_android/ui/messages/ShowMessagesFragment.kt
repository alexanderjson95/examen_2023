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
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.map
import kotlin.collections.mapNotNull
import kotlin.getValue

@AndroidEntryPoint
class ShowMessagesFragment : Fragment(R.layout.create_message) {

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
        val input = view.findViewById<MaterialTextView>(R.id.user_edit_query)
        val sendBtn = view.findViewById<MaterialButton>(R.id.user_searchBtn)

        vm.users.observe(viewLifecycleOwner){
                users ->
                    if (users != null){
                    adapter.submitList(users)
                }
            }


        sendBtn.setOnClickListener {
            Log.d("Users", "No users found")
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                vm.searchUsers("username", text)
            } else {
                vm.getUsers()
            }
        }
    }


}



//private val vm: MessageViewModel by activityViewModels()
//
//private var recipientId: Long = 0L
//private val args: ShowMessagesFragmentArgs by navArgs()
//
//private var showContacts: Boolean = false;
//
//private lateinit var adapter: ShowMessagesAdapter
//
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//    val recyclerView = view.findViewById<RecyclerView>(R.id.usersAddRecyclerView)
//    val checkBox = view.findViewById<CheckBox>(R.id.showMembersCheck)
//
//    recipientId = args.recipientId
//
//    vm.messages.observe(viewLifecycleOwner) { message ->
//        val members = vm.messages.value
//        if (message != null && members != null) {
//            val recipientIds = members.mapNotNull { it.recipientId }.toSet()
//            adapter.updateId(recipientIds)
//            //adapter.submitList(filterMembers(message, members))
//            Log.d("InviteUserFragment", "Fetched users: ${message.map { it.id }}")
//            Log.d("InviteUserFragment", "Fetched recipients: $recipientIds")
//
//        }
//    }
//
//
//    // lägg navigering till meddelande lista här
//    adapter = ShowMessagesAdapter(
//        openMessage = {
//            val action = ShowMessagesFragmentDirections.dashToWriteMessage(recipientId)
//            findNavController().navigate(action)
//        }
//    )
//
//
//    // visa users som är recipients och icke rdcipients
//    checkBox.setOnCheckedChangeListener { _, isChecked ->
//        showContacts = isChecked
//    }
//
//    recyclerView.layoutManager = LinearLayoutManager(requireContext())
//    recyclerView.adapter = adapter
//
//    val input = view.findViewById<EditText>(R.id.user_edit_query)
//    val sendBtn = view.findViewById<MaterialButton>(R.id.user_searchBtn)
//
//    sendBtn.setOnClickListener {
//        val text = input.text.toString()
//        if (text.isNotEmpty()) {
//            // sök users här
//            vm.sendMessage(recipientId, "")
//        } else {
//            Log.d("Messages", "MESSAGE: TYPE IN TEXT!!!!")
//        }
//    }
//}
//
//private fun filterMembers(
//    userList: List<UserResponse>,
//    memberList: List<UserProjectResponse>
//): List<UserResponse> {
//    val membersIds = memberList.mapNotNull { it.userId }.toHashSet()
//    return if (showContacts) {
//        userList.filter { it.id in membersIds }
//    } else {
//        userList.filter { it.id !in membersIds }
//    }
//}
//}