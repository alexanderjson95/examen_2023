package com.example.frontend_android.ui.messages

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//@AndroidEntryPoint
//class MessageFragment : Fragment(R.layout.fragment_messages){
//    private val vm: MessageViewModel by activityViewModels()
//    @Inject
//    lateinit var adapter: MessageAdapter
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val recyclerView = view.findViewById<RecyclerView>(R.id.messagesRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = adapter
//        observeViewModel()
//        vm.getUserMessages()
//
//        val input = view.findViewById<EditText>(R.id.message_edit_text)
//        val sendBtn = view.findViewById<MaterialButton>(R.id.sendBtn)
//        sendBtn.setOnClickListener {
//            vm.getUserMessages()
//        }
//
//    }
//
//    private fun observeViewModel(){
//        vm.messages.observe(viewLifecycleOwner){
//                message ->
//            if (message != null){
//                adapter.submitList(message)
//            }
//        }
//    }
//}