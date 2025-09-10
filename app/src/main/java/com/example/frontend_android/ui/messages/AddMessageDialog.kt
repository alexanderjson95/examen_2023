package com.example.frontend_android.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.frontend_android.R
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.user.UsersViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AddMessageDialog : DialogFragment() {
//TODO optimera allt    OCH ÄVEN gör så att om man skickar till sig själv blir det anteckning
    // todo gör fel check osv sedan
    private val vm: MessageViewModel by activityViewModels()
    private val um: UsersViewModel by activityViewModels()

    var chosenUsername: String? = ""
    var chosenUser: UserResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_message_form, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val senderName = view.findViewById<EditText>(R.id.sender_value)
        val msgContent = view.findViewById<EditText>(R.id.content_value)
        val addBtn = view.findViewById<MaterialButton>(R.id.create_msg_btn)
        val closeBtn = view.findViewById<MaterialButton>(R.id.close_dialog_btn)
        val userDropdown: AutoCompleteTextView = view.findViewById(R.id.userDropdown)
        val name_key = view.findViewById<TextView>(R.id.name_key)


        val suggestionAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            mutableListOf()
        )
        userDropdown.setAdapter(suggestionAdapter)


        userDropdown.addTextChangedListener { u ->
            val text = u?.toString() ?: ""
            if (text.isNotEmpty()) {
                um.searchUsers("username", text)
            }
        }

        um.users.observe(viewLifecycleOwner) { u ->
            val username = u.map { it.username }
            suggestionAdapter.clear()
            suggestionAdapter.addAll(username)
            suggestionAdapter.notifyDataSetChanged()
            userDropdown.showDropDown()
        }


        userDropdown.setOnItemClickListener { _, _, position, _ ->
            chosenUsername = suggestionAdapter.getItem(position)!!
            userDropdown.setText(chosenUsername,false)
            name_key.text = chosenUsername

        }

        addBtn.setOnClickListener {
            if(chosenUsername == null){
                Toast.makeText(requireContext(), "Du måste välja en mottagare från listan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (msgContent.text.toString().isBlank()) {
                Toast.makeText(requireContext(), "Meddelandet måste ha innehåll", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            chosenUser = um.returnUserByUsername(chosenUsername!!)

            vm.sendMessage(
                chosenUser?.id,
                msgContent.text.toString() //rad 78
            )

            vm.state.observe(viewLifecycleOwner) { s ->
                Toast.makeText(requireContext(), "Result:  $s", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(requireContext(), "Chosen:  $chosenUsername", Toast.LENGTH_SHORT).show()

        }
        isCancelable = true

    }



    override fun onStart(){
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}