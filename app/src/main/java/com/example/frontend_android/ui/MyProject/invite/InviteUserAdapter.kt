package com.example.frontend_android.ui.MyProject.invite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.schedule.MemberAdapter
import com.google.android.material.button.MaterialButton

class InviteUserAdapter  (
    private val addUser: (Long) -> Unit, private var memberIds: Set<Long> = emptySet()):
    ListAdapter<UserResponse, InviteUserAdapter.InviteUserViewHolder>(DiffCallback())
{


    class InviteUserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var fName_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)
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
        val isMember = memberIds.contains(users.id)

        if (isMember) {
            holder.acceptBtn.isEnabled = false
            holder.acceptBtn.text = "Inbjuden"
        }
        else
        {
            holder.acceptBtn.isEnabled = true
        }

        holder.acceptBtn.setOnClickListener {
            addUser(users.id)
            holder.acceptBtn.isEnabled = false // överdriven extra säkerhet
            holder.acceptBtn.text = "Inbjuden"
        }
    }

    fun updateId(newMemberIds: Set<Long>) {
        memberIds = newMemberIds
        notifyDataSetChanged()
    }



    class DiffCallback : DiffUtil.ItemCallback<UserResponse> (){
        override fun areItemsTheSame(
            oldItem: UserResponse,
            newItem: UserResponse
        ): Boolean {
            return oldItem.id == newItem.id //behöver bara id för att ha koll på raderna
        }

        override fun areContentsTheSame(
            oldItem: UserResponse,
            newItem: UserResponse
        ): Boolean {
           return  oldItem == newItem // behöver hela objekt för att jämnföra innehåll
        }

    }
}