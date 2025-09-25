package com.example.frontend_android.ui.project

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

class ProjectAdapter(private val add: (Long) -> Unit):
        RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>(){
            private var projectList: List<ProjectResponse> = emptyList()
    class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title_value: TextView = view.findViewById(R.id.name_value)
        var description_value: TextView = view.findViewById(R.id.description_value)
        var acceptBtn: MaterialButton = view.findViewById(R.id.acceptBtn)

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
            .inflate(R.layout.item_project_request, parent, false)
        return ProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ProjectViewHolder,
        position: Int
    ) {
        val projects = projectList[position]
        holder.title_value.text = projects.projectName
        holder.description_value.text = projects.description
        holder.acceptBtn.setOnClickListener {
            add(projects.id)
        }

    }

    override fun getItemCount(): Int {
        return projectList.size
    }


}