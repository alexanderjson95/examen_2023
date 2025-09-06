package com.example.frontend_android.ui.user

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Users.UserResponse
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class UsersAdapter @Inject constructor():
        RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(){

            private var userList: List<UserResponse> = emptyList()

    interface ReturnUsername{
        fun onFetchAttempt(username: String);
    }


    var returnUsername: ReturnUsername? = null
    class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var user_name: TextView = view.findViewById(R.id.username_value)
        var user_fname: TextView = view.findViewById(R.id.fname_value)
        var user_lname: TextView = view.findViewById(R.id.lname_value)

        val writeBtn = view.findViewById<MaterialButton>(R.id.write_btn)
    }

    fun submitList(newList: List<UserResponse>){
        userList = newList
        notifyDataSetChanged() //todo
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersAdapter.UsersViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(itemView)
    }



    override fun onBindViewHolder(
        holder: UsersViewHolder,
        position: Int
    ) {
        val userresponse = userList[position]
        holder.user_name.text = userresponse.username
        holder.user_fname.text = "${userresponse.firstName}"
        holder.user_lname.text = "${userresponse.lastName}"
        holder.writeBtn.setOnClickListener {
            returnUsername?.onFetchAttempt(userresponse.username)
        }
    }



    override fun getItemCount(): Int {
        return userList.size
    }

}