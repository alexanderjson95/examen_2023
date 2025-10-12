package com.example.frontend_android.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class UserProjectAdapter @Inject constructor(
    private val navigateOnClick: (Long) -> Unit
) : ListAdapter<UserProjectResponse, UserProjectAdapter.UserProjectViewHolder>(DiffCallback()) {

    class UserProjectViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var project_key: TextView = view.findViewById(R.id.project_key)
        val openBtn: MaterialButton = view.findViewById(R.id.open_btn)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserProjectAdapter.UserProjectViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_wide, parent, false)
        return UserProjectViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: UserProjectViewHolder, position: Int) {
        val project = getItem(position)
        holder.project_key.text = project.projectName
        val projectId = project.projectId
        holder.openBtn.setOnClickListener { navigateOnClick(projectId) }
    }


    class DiffCallback : DiffUtil.ItemCallback<UserProjectResponse>() {
        override fun areItemsTheSame(oldItem: UserProjectResponse, newItem: UserProjectResponse) =
            oldItem.projectId == newItem.projectId

        override fun areContentsTheSame(oldItem: UserProjectResponse, newItem: UserProjectResponse) =
            oldItem == newItem
    }

}