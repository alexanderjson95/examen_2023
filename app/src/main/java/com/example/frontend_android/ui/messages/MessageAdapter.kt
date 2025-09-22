package com.example.frontend_android.ui.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.MyProject.invite.InviteUserAdapter
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MessageAdapter @Inject constructor(

    private val addUser: (Long) -> Unit,private val removeUser: (Long) -> Unit,
    private var memberIds: Set<Long> = emptySet()):
    ListAdapter<UserResponse, MessageAdapter.MessageViewHolder>(DiffCallback()) {


    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var fName_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)
        var acceptBtn: MaterialButton = view.findViewById(R.id.acceptBtn)
        var removeBtn: MaterialButton = view.findViewById(R.id.removeBtn)

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_add, parent, false)
        return MessageViewHolder(itemView)
    }



    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {
        val users = getItem(position)
        holder.fName_value.text = users.firstName
        holder.lname_value.text = users.lastName
    }




    class DiffCallback : DiffUtil.ItemCallback<UserResponse> (){
        override fun areItemsTheSame(
            oldItem: UserResponse,
            newItem: UserResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserResponse,
            newItem: UserResponse
        ): Boolean {
            return  oldItem == newItem
        }

    }
}