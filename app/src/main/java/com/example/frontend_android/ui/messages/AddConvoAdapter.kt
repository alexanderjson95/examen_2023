package com.example.frontend_android.ui.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Users.UserResponse
import com.google.android.material.button.MaterialButton

class AddConvoAdapter  (
    private val openMessage: (Long, String, String) -> Unit, private var friendIds: Set<Long> = emptySet()):
    ListAdapter<UserResponse, AddConvoAdapter.ShowMessagesViewHolder>(DiffCallback()) {


    class ShowMessagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recipient_key: TextView = view.findViewById(R.id.fname_value)
        var recipient_value: TextView = view.findViewById(R.id.lname_value)
        var openBtn: MaterialButton = view.findViewById(R.id.open_btn)
        var contactCard: CardView = view.findViewById(R.id.contactCard)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowMessagesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ShowMessagesViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ShowMessagesViewHolder,
        position: Int
    ) {
        val users = getItem(position)
        holder.recipient_key.text = users.firstName
        holder.recipient_value.text = users.lastName
        val isFriend = friendIds.contains(users.id)
        holder.contactCard.setOnClickListener {
            openMessage(users.id, users.firstName, users.lastName)
        }
    }

    fun updateId(newMemberIds: Set<Long>) {
        friendIds = newMemberIds
        notifyDataSetChanged()
    }


    class DiffCallback : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(
            oldItem: UserResponse,
            newItem: UserResponse
        ): Boolean {
            return oldItem.id == newItem.id //behöver bara id för att ha koll på raderna
        }

        override fun areContentsTheSame(
            oldItem: UserResponse,
            newItem: UserResponse
        ): Boolean {
            return oldItem == newItem // behöver hela objekt för att jämnföra innehåll
        }

    }
}