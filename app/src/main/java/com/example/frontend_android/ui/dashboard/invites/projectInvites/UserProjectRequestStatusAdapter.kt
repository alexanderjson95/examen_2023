package com.example.frontend_android.ui.dashboard.invites.projectInvites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import androidx.core.view.isVisible

@FragmentScoped
class UserProjectRequestStatusAdapter @Inject constructor(private val accept: (Long, Long) -> Unit,private val remove: (Long, Long) -> Unit):
    RecyclerView.Adapter<UserProjectRequestStatusAdapter.UserProjectRequestViewholder>(){
    private var projectList: List<UserProjectResponse> = emptyList()
    class UserProjectRequestViewholder(view: View) : RecyclerView.ViewHolder(view){
        var name_value: TextView = view.findViewById(R.id.name_value)
        var request_value: TextView = view.findViewById(R.id.request_value)

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
            .inflate(R.layout.item_project_request, parent, false)
        return UserProjectRequestViewholder(itemView)
    }

    override fun onBindViewHolder(
        holder: UserProjectRequestViewholder,
        position: Int
    ) {
        val members = projectList[position]
        holder.name_value.text = "${members.projectName}"
        holder.acceptBtn.setOnClickListener {
            accept(members.projectId, members.userId)
        }
        holder.removeBtn.setOnClickListener {
            remove(members.projectId, members.userId)
        }



        when(members.requestType){
            "INVITE" -> {
                holder.acceptBtn.isVisible = true
                holder.removeBtn.isVisible = true
                "Inbjudan".also { holder.request_value.text = it }

            }
            "REQUEST" -> {
                holder.acceptBtn.isVisible = false
                holder.removeBtn.isVisible = true
                "Förfrågan från dig".also { holder.request_value.text = it }
            }
            "DECLINED" -> {
                holder.acceptBtn.isVisible = false
                holder.removeBtn.isVisible = false
                "Nekat".also { holder.request_value.text = it }
            }
            "ACCEPTED" -> {
                holder.acceptBtn.isVisible = false
                holder.removeBtn.isVisible = false
                "Medlem".also { holder.request_value.text = it }
            }
        }

    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}