package com.example.frontend_android.ui.userProject.schedule

import dagger.hilt.android.scopes.FragmentScoped


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.google.android.material.button.MaterialButton

@FragmentScoped
class CreateGroupAdapter  (
    private val addUser: (Long) -> Unit,
    private var groupMembers: Set<Long> = emptySet(),
    private val removeUser: (Long) -> Unit):

    ListAdapter<UserProjectResponse, CreateGroupAdapter.InviteUserViewHolder>(DiffCallback())
{


    class InviteUserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var fName_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)

        var acceptBtn: MaterialButton = view.findViewById(R.id.add_btn)
        var removeBtn: MaterialButton = view.findViewById(R.id.remove_btn)

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):InviteUserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group, parent, false)
        return InviteUserViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: InviteUserViewHolder,
        position: Int
    ) {
        val users = getItem(position)
        holder.fName_value.text = users.firstName
        holder.lname_value.text = users.lastName

        val inGroup = groupMembers.contains(users.userId)
        holder.acceptBtn.apply {
            isEnabled = !inGroup
            text = if (inGroup) "Inlagd" else "Lägg till"
            setOnClickListener {
                if (!inGroup) addUser(users.userId)
            }
        }

        holder.removeBtn.apply {
            isEnabled = inGroup
            visibility = if (inGroup) View.VISIBLE else View.GONE
            setOnClickListener {
                if (inGroup) removeUser(users.userId)
            }
        }



    }

    fun updateId(newMemberIds: Set<Long>) {
        groupMembers = newMemberIds
        notifyDataSetChanged()
    }



    class DiffCallback : DiffUtil.ItemCallback<UserProjectResponse> (){
        override fun areItemsTheSame(
            oldItem: UserProjectResponse,
            newItem: UserProjectResponse
        ): Boolean {
            return oldItem.userId == newItem.userId //behöver bara id för att ha koll på raderna
        }

        override fun areContentsTheSame(
            oldItem: UserProjectResponse,
            newItem: UserProjectResponse
        ): Boolean {
            return  oldItem == newItem // behöver hela objekt för att jämnföra innehåll
        }

    }
}