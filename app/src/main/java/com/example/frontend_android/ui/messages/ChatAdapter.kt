package com.example.frontend_android.ui.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Chat.MessageResponse
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ChatAdapter @Inject constructor():
    ListAdapter<MessageResponse, ChatAdapter.ChatAdapterViewHolder>(DiffCallback()) {


    class ChatAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var fName_value: TextView = view.findViewById(R.id.fname_value)
        var content: TextView = view.findViewById(R.id.content)
        var date_value: TextView = view.findViewById(R.id.date_value)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ChatAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message_chat, parent, false)
        return ChatAdapterViewHolder(itemView)
    }



    override fun onBindViewHolder(
        holder: ChatAdapterViewHolder,
        position: Int
    ) {
        val message = getItem(position)
        holder.fName_value.text = message.senderFirstname
        holder.content.text = message.content
        holder.date_value.text = message.created

    }




    class DiffCallback : DiffUtil.ItemCallback<MessageResponse> (){
        override fun areItemsTheSame(
            oldItem: MessageResponse,
            newItem: MessageResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MessageResponse,
            newItem: MessageResponse
        ): Boolean {
            return  oldItem == newItem
        }

    }
}