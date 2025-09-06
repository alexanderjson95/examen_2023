package com.example.frontend_android.ui.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MessageAdapter @Inject constructor():
        RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){

            private var messageList: List<MessageResponse> = emptyList()
    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var sender_value: TextView = view.findViewById(R.id.sender_value)
        var recipient_value: TextView = view.findViewById(R.id.recipient_value)
        var content_value: TextView = view.findViewById(R.id.content_value)
        var date_value: TextView = view.findViewById(R.id.date_value)
    }

    fun submitList(newList: List<MessageResponse>){
        messageList = newList
        notifyDataSetChanged() //todo
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageAdapter.MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {
        //todo länk på username till profil
        val message = messageList[position]
        holder.sender_value.text = "${message.senderUsername}" //todo map usernames från users
        holder.recipient_value.text = "${message.recipientUsername}"
        holder.content_value.text = "${message.content}"
        holder.date_value.text = "${message.created}"
    }

    override fun getItemCount(): Int {
        return messageList.size
    }


}