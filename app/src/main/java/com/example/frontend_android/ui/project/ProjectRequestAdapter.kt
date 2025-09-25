package com.example.frontend_android.ui.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.google.android.material.button.MaterialButton

class ProjectRequestAdapter  (
    private val add: (Long) -> Unit, private var requestedIds: Set<Long> = emptySet()):
    ListAdapter<UserProjectResponse, ProjectRequestAdapter.InviteUserViewHolder>(DiffCallback())
{
    class InviteUserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title_value: TextView = view.findViewById(R.id.name_value)
        var description_value: TextView = view.findViewById(R.id.description_value)
        var acceptBtn: MaterialButton = view.findViewById(R.id.acceptBtn)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):InviteUserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_request, parent, false)
        return InviteUserViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: InviteUserViewHolder,
        position: Int
    ) {
        val projects = getItem(position)
        holder.title_value.text = projects.projectName
        holder.description_value.text = projects.projectDescription

        val isMember = requestedIds.contains(projects.userId)

        if (isMember) {
            holder.acceptBtn.isEnabled = false
            holder.acceptBtn.text = "Inbjuden"
        }
        else
        { holder.acceptBtn.isEnabled = false
            holder.acceptBtn.text = " "

        }

        holder.acceptBtn.setOnClickListener {
            add(projects.userId)  // lägger in userId ur userProject
            holder.acceptBtn.isEnabled = false // överdriven extra säkerhet
            holder.acceptBtn.text = " "
        }

    }

    fun updateId(newMemberIds: Set<Long>) {
        requestedIds = newMemberIds
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