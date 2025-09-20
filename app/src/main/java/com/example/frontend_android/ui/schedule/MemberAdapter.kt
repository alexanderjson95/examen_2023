package com.example.frontend_android.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.roles.RoleResponse
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

class MemberAdapter(private val removeUser: (Long) -> Unit):
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){

    private var memberList: List<UserProjectResponse> = emptyList()




    class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var fname_value: TextView = view.findViewById(R.id.fname_value)
        var lname_value: TextView = view.findViewById(R.id.lname_value)

        var removeBtn: MaterialButton = view.findViewById(R.id.removeBtn)


    }

    fun submitList(newList: List<UserProjectResponse>){
        memberList = newList
        notifyDataSetChanged() //todo
    }


    fun returnUser(memberId: Long): Long {
        return memberId;
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

        holder.removeBtn.setOnClickListener {
            removeUser(members.userId ?: -1L)
        }
    }

    override fun getItemCount(): Int {
        return memberList.size
    }


}