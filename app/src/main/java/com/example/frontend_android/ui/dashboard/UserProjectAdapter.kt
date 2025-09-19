package com.example.frontend_android.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class UserProjectAdapter @Inject constructor(private val navigateOnClick: (Long) -> Unit):
        RecyclerView.Adapter<UserProjectAdapter.UserProjectViewHolder>(){

            private var userProjectList: List<UserProjectResponse> = emptyList()
    class UserProjectViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var project_value: TextView = view.findViewById(R.id.project_value)
        val openBtn: MaterialButton = view.findViewById(R.id.open_btn)
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
            .inflate(R.layout.item_project_wide, parent, false)
        return UserProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: UserProjectViewHolder,
        position: Int
    ) {
        val projects = userProjectList[position]
        holder.project_value.text = "${projects.projectName}"
        val projectId = projects.projectId ?: -1L
        holder.openBtn.setOnClickListener {
            navigateOnClick(projectId)
        }

    }

    override fun getItemCount(): Int {
        return userProjectList.size
    }



}