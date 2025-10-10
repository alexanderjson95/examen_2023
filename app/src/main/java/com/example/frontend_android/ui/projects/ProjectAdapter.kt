package com.example.frontend_android.ui.projects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.ProjectResponse
import com.google.android.material.button.MaterialButton

class ProjectAdapter(private val add: (Long) -> Unit):
    ListAdapter<ProjectResponse, ProjectAdapter.ProjectViewHolder>(DiffCallback()){
    class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title_value: TextView = view.findViewById(R.id.name_value)
        var description_value: TextView = view.findViewById(R.id.description_value)
        var acceptBtn: MaterialButton = view.findViewById(R.id.acceptBtn)

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
        val project = getItem(position)
        holder.title_value.text = project.projectName
        holder.description_value.text = project.description
        holder.acceptBtn.setOnClickListener {
            add(project.id)

        }

    }


    class DiffCallback : DiffUtil.ItemCallback<ProjectResponse>() {
        override fun areItemsTheSame(oldItem: ProjectResponse, newItem: ProjectResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProjectResponse, newItem: ProjectResponse): Boolean {
            return oldItem == newItem
        }
    }
}