package com.example.frontend_android.ui.user

import android.os.Bundle
import android.util.Log
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

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users){
    private val vm: UsersViewModel by activityViewModels()
    @Inject
    lateinit var adapter: UsersAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.usersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter



        val input = view.findViewById<EditText>(R.id.user_edit_query)
        val sendBtn = view.findViewById<MaterialButton>(R.id.user_searchBtn)



        sendBtn.setOnClickListener {
            Log.d("MovieLike", "No movie found to like")
            observeViewModel()
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                vm.searchUsers("username", text)
            }
        }


    }

    private fun observeViewModel(){
        vm.users.observe(viewLifecycleOwner){
                users ->
            if (users != null){
                adapter.submitList(users)
            }
        }
    }
}