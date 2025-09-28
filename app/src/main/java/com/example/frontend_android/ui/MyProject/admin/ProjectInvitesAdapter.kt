package com.example.frontend_android.ui.MyProject.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class ProjectInvitesAdapter @Inject constructor(private val accept: (Long, Long) -> Unit,private val remove: (Long, Long) -> Unit):
    RecyclerView.Adapter<ProjectInvitesAdapter.UserProjectRequestViewholder>(){
    private var projectList: List<UserProjectResponse> = emptyList()
    class UserProjectRequestViewholder(view: View) : RecyclerView.ViewHolder(view){
        var fname_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)
        var date_value: TextView = view.findViewById(R.id.date_value)

        var acceptBtn: TextView = view.findViewById(R.id.acceptBtn)
        var removeBtn: TextView = view.findViewById(R.id.removeBtn)



    }

    fun submitList(newList: List<UserProjectResponse>){
        projectList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserProjectRequestViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_request_invite, parent, false)
        return UserProjectRequestViewholder(itemView)
    }

    override fun onBindViewHolder(
        holder: UserProjectRequestViewholder,
        position: Int
    ) {
        val members = projectList[position]
        holder.fname_value.text = "${members.firstName}"
        holder.lname_value.text = "${members.lastName}"
        holder.date_value.text = members.requestedDate
        holder.acceptBtn.isVisible = members.requestType != "REQUEST"
        holder.removeBtn.setOnClickListener {
            remove(members.projectId, members.userId)
        }
        holder.acceptBtn.setOnClickListener {
            accept(members.projectId, members.userId)
        }

    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}