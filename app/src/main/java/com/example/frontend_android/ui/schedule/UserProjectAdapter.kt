package com.example.frontend_android.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class UserProjectAdapter @Inject constructor():
        RecyclerView.Adapter<UserProjectAdapter.UserProjectViewholder>(){

            private var projectList: List<UserProjectResponse> = emptyList()
    class UserProjectViewholder(view: View) : RecyclerView.ViewHolder(view){
        var username_value: TextView = view.findViewById(R.id.username_value)
    }

    fun submitList(newList: List<UserProjectResponse>){
        projectList = newList
        notifyDataSetChanged() //todo
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserProjectViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserProjectViewholder(itemView)
    }

    override fun onBindViewHolder(
        holder: UserProjectViewholder,
        position: Int
    ) {
        val members = projectList[position]
        holder.username_value.text = "${members.username}"
    }

    override fun getItemCount(): Int {
        return projectList.size
    }


}