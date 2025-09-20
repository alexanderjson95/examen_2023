package com.example.frontend_android.ui.MyProject.invite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.schedule.MemberAdapter

class InviteUserAdapter  (
    private val addUser: (Long) -> Unit):
    RecyclerView.Adapter<InviteUserAdapter.InviteUserViewHolder>()
{ private var userList: List<UserResponse> = emptyList()

    class InviteUserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var fName_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)
    }

    fun submitList(newList: List<UserResponse>){
        userList = newList
        notifyDataSetChanged() //todo
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InviteUserAdapter.InviteUserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_add, parent, false)
        return InviteUserViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: InviteUserViewHolder,
        position: Int
    ) {
        val users = userList[position]
        holder.fName_value.text = users.firstName
        holder.lname_value.text = users.lastName
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}