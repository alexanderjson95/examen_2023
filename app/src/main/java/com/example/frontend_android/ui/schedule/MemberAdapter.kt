package com.example.frontend_android.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.ui.project.ProjectAdapter
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MemberAdapter @Inject constructor():
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){

    private var memberList: List<UserProjectResponse> = emptyList()
    class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var fname_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)


    }

    fun submitList(newList: List<UserProjectResponse>){
        memberList = newList
        notifyDataSetChanged() //todo
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_member, parent, false)
        return MemberViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MemberViewHolder,
        position: Int
    ) {
        val members = memberList[position]
        holder.fname_value.text = "${members.firstName}"
        holder.lname_value.text = "${members.lastName}"

    }

    override fun getItemCount(): Int {
        return memberList.size
    }


}