package com.example.frontend_android.ui.userProject.projectInvites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import androidx.core.view.isVisible


@FragmentScoped
class ProjectUserRequestStatusAdapter @Inject constructor(private val accept: (Long, Long) -> Unit,private val remove: (Long, Long) -> Unit):
    RecyclerView.Adapter<ProjectUserRequestStatusAdapter.UserProjectRequestViewholder>(){
    private var projectList: List<UserProjectResponse> = emptyList()
    class UserProjectRequestViewholder(view: View) : RecyclerView.ViewHolder(view){
        var request_value: TextView = view.findViewById(R.id.req_type)
        var fname_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)

        var acceptBtn: TextView = view.findViewById(R.id.acceptBtn)
        var removeBtn: TextView = view.findViewById(R.id.removeBtn)
        var creatorIcon: ImageView = view.findViewById(R.id.creator)
        var role_value: TextView = view.findViewById(R.id.role_value)



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
            .inflate(R.layout.item_projectuser_request_alt, parent, false)
        return UserProjectRequestViewholder(itemView)
    }

    override fun onBindViewHolder(
        holder: UserProjectRequestViewholder,
        position: Int
    ) {
        val members = projectList[position]
        holder.fname_value.text = "${members.firstName}"
        holder.lname_value.text = "${members.lastName}"
        holder.role_value.text = "${members.role}"
        holder.creatorIcon.isVisible = members.isAdmin

        holder.acceptBtn.setOnClickListener {
            accept(members.projectId, members.userId)
        }
        holder.removeBtn.setOnClickListener {
            remove(members.projectId, members.userId)
        }



        when(members.requestType){
            "INVITE" -> {
                holder.acceptBtn.isVisible = false
                holder.removeBtn.isVisible = false
                "Skickat förfrågan".also { holder.request_value.text = it }
                holder.acceptBtn.text = "Acceptera"
                holder.removeBtn.text = "Neka"

            }
            "REQUEST" -> {
                holder.acceptBtn.isVisible = true
                holder.removeBtn.isVisible = true
                holder.removeBtn.text = "Neka"
                "Vill gå med i projektet".also { holder.request_value.text = it }
            }
            "DECLINED" -> {
                holder.acceptBtn.isVisible = false
                holder.removeBtn.isVisible = false
                "Nekat".also { holder.request_value.text = it }
            }
            "ACCEPTED" -> {
                holder.acceptBtn.isVisible = false
                holder.removeBtn.isVisible = true
                "Medlem".also { holder.request_value.text = it }
                holder.removeBtn.text = "Kasta ut"

            }
        }

    }
    override fun getItemCount(): Int {
        return projectList.size
    }
}