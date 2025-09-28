package com.example.frontend_android.ui.MyProject.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class ProjectAddUsersAdapter @Inject constructor(private val accept: (Long) -> Unit):
    RecyclerView.Adapter<ProjectAddUsersAdapter.UserProjectRequestViewholder>(){
    private var projectList: List<UserRoleResponse> = emptyList()
    class UserProjectRequestViewholder(view: View) : RecyclerView.ViewHolder(view){
        var fname_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)
        var role_value: TextView = view.findViewById(R.id.role_value)

        var acceptBtn: TextView = view.findViewById(R.id.acceptBtn)
    }

    fun submitList(newList: List<UserRoleResponse>){
        projectList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserProjectRequestViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_add, parent, false)
        return UserProjectRequestViewholder(itemView)
    }

    override fun onBindViewHolder(
        holder: UserProjectRequestViewholder,
        position: Int
    ) {
        val members = projectList[position]
        holder.fname_value.text = members.firstName
        holder.lname_value.text = members.lastName
        holder.role_value.text = members.roleType

        holder.acceptBtn.setOnClickListener {
            accept(members.userId)
        }

    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}