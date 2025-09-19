package com.example.frontend_android.ui.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.ProjectResponse
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ProjectAdapter @Inject constructor():
        RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>(){

            private var projectList: List<ProjectResponse> = emptyList()
    class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var project_value: TextView = view.findViewById(R.id.project_value)
    }

    fun submitList(newList: List<ProjectResponse>){
        projectList = newList
        notifyDataSetChanged() //todo
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ProjectViewHolder,
        position: Int
    ) {
        val projects = projectList[position]
        holder.project_value.text = "${projects.projectName}"
    }

    override fun getItemCount(): Int {
        return projectList.size
    }


}