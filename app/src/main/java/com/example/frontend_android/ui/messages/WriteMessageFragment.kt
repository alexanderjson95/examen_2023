package com.example.frontend_android.ui.messages

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class WriteMessageFragment : Fragment(R.layout.fragment_inchat) {
    private val vm: MessageViewModel by activityViewModels()

    private var recipientId: Long = 0L
    private var fName: String= ""
    private var lName: String = ""

    private val args: WriteMessageFragmentArgs by navArgs()
    @Inject lateinit var adapter: ChatAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.messagesRecyclerView)
        recipientId = args.recipientId
        fName = args.recipientFname
        lName = args.recipientLname


        vm.openChat(recipientId)
        vm.messages.observe(viewLifecycleOwner) { m ->
            if (m != null) {
                val sender = m.map { it.senderFirstname }
                val recipient = m.map { it.recipientId }
                adapter.submitList(m)
                Log.d("Messages", "MESSAGE: TYPE IN TEXT!!!!")
                Log.d("Messages", "MESSAGE: sender: $sender and recipient $recipient  ")

            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val input = view.findViewById<TextInputEditText>(R.id.message_edit_text)
        val sendBtn = view.findViewById<MaterialButton>(R.id.sendBtn)
        val fname_value = view.findViewById<TextView>(R.id.fname_value)
        val lName_value = view.findViewById<TextView>(R.id.lname_value)

        fname_value.text = fName
        lName_value.text = lName

        sendBtn.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                // skicka meddelande här
                vm.sendMessage(recipientId, text)
                val newList = vm.messages.value
                adapter.submitList(newList?.toList())
                // lägg på refresh UI här
            } else {
                Log.d("Messages", "MESSAGE: TYPE IN TEXT!!!!")
            }
        }
    }

}