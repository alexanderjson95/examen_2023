package com.example.frontend_android.ui.dashboard

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
        RecyclerView.Adapter<UserProjectAdapter.UserProjectViewHolder>(){

            private var userProjectList: List<UserProjectResponse> = emptyList()
    class UserProjectViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var project_value: TextView = view.findViewById(R.id.project_value)
        var genre_value: TextView = view.findViewById(R.id.genre_value)
    }

    fun submitList(newList: List<UserProjectResponse>){
        userProjectList = newList
        notifyDataSetChanged() //todo
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserProjectAdapter.UserProjectViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
        return UserProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: UserProjectViewHolder,
        position: Int
    ) {
        val projects = userProjectList[position]
        holder.project_value.text = "${projects.projectName}"
        holder.genre_value.text = "${projects.role}"
    }

    override fun getItemCount(): Int {
        return userProjectList.size
    }


}