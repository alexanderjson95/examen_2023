package com.example.frontend_android.ui.MyProject.invite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.ui.schedule.MemberAdapter
import com.google.android.material.button.MaterialButton

class InviteUserAdapter  (
    private val addUser: (Long) -> Unit,private val removeUser: (Long) -> Unit, private var memberIds: Set<Long> = emptySet()):
    ListAdapter<UserRoleResponse, InviteUserAdapter.InviteUserViewHolder>(DiffCallback())
{


    class InviteUserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var fName_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)
        var role_value: TextView = view.findViewById(R.id.role_value)

        var acceptBtn: MaterialButton = view.findViewById(R.id.acceptBtn)

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):InviteUserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_add, parent, false)
        return InviteUserViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: InviteUserViewHolder,
        position: Int
    ) {
        val users = getItem(position)
        holder.fName_value.text = users.firstName
        holder.lname_value.text = users.lastName
        holder.role_value.text = users.roleType

        val isMember = memberIds.contains(users.userId)

        if (isMember) {
            holder.acceptBtn.isEnabled = false
            holder.acceptBtn.text = "Inbjuden"

        }
        else
        {
            holder.acceptBtn.isEnabled = true
        }

        holder.acceptBtn.setOnClickListener {
            addUser(users.userId)  // lägger in userId ur userProject
            holder.acceptBtn.isEnabled = false // överdriven extra säkerhet
            holder.acceptBtn.text = "Inbjuden"
        }

    }

    fun updateId(newMemberIds: Set<Long>) {
        memberIds = newMemberIds
        notifyDataSetChanged()
    }



    class DiffCallback : DiffUtil.ItemCallback<UserRoleResponse> (){
        override fun areItemsTheSame(
            oldItem: UserRoleResponse,
            newItem: UserRoleResponse
        ): Boolean {
            return oldItem.userId == newItem.userId //behöver bara id för att ha koll på raderna
        }

        override fun areContentsTheSame(
            oldItem: UserRoleResponse,
            newItem: UserRoleResponse
        ): Boolean {
           return  oldItem == newItem // behöver hela objekt för att jämnföra innehåll
        }

    }
}