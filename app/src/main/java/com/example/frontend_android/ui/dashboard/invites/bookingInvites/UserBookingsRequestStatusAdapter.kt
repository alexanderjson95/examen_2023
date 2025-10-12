package com.example.frontend_android.ui.dashboard.invites.bookingInvites

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import androidx.core.view.isVisible
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.BookingStatusType
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneId.systemDefault

@FragmentScoped
class UserBookingsRequestStatusAdapter @Inject constructor(private val add: (Long, Long) -> Unit,private val remove: (Long,Long) -> Unit):
    RecyclerView.Adapter<UserBookingsRequestStatusAdapter.UserBookingsRequestViewholder>(){
    private var BookingResponse: List<BookingResponse> = emptyList()
    class UserBookingsRequestViewholder(view: View) : RecyclerView.ViewHolder(view){
        var name_value: TextView = view.findViewById(R.id.name_value)
        var date: TextView = view.findViewById(R.id.date)

        var hourStart: TextView = view.findViewById(R.id.hourstart)
        var minStart: TextView = view.findViewById(R.id.minstart)

        var hourEnd: TextView = view.findViewById(R.id.hourend)
        var minEnd: TextView = view.findViewById(R.id.minend)
        var requestType: TextView = view.findViewById(R.id.request_type)


        var acceptBtn: TextView = view.findViewById(R.id.acceptBtn)
        var removeBtn: TextView = view.findViewById(R.id.removeBtn)



    }

    fun submitList(newList: List<BookingResponse>){
        BookingResponse = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserBookingsRequestViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookings_request, parent, false)
        return UserBookingsRequestViewholder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: UserBookingsRequestViewholder,
        position: Int
    ) {
        val bookings = BookingResponse[position]
        val millisFromDb: Long = bookings.dateMillis
        val localDate = Instant.ofEpochMilli(millisFromDb)
            .atZone(systemDefault())
            .toLocalDate()
        holder.name_value.text = bookings.projectName
        holder.date.text = localDate.toString()
        String.format("%02d", bookings.startHour).also { holder.hourStart.text = it }
        String.format("%02d", bookings.startMinute).also { holder.minStart.text = it }
        String.format("%02d", bookings.endHour).also { holder.hourEnd.text = it }
        String.format("%02d", bookings.endMinute).also { holder.minEnd.text = it }


        when(bookings.status){
            BookingStatusType.INVITE -> {
                holder.acceptBtn.isVisible = true
                holder.removeBtn.isVisible = true
                "Inbjudan".also { holder.requestType.text = it }

            }
            BookingStatusType.ADMIN -> {
                holder.acceptBtn.isVisible = false
                holder.removeBtn.isVisible = true
                "Godkänd bokning (skapad av mig)".also { holder.requestType.text = it }
            }

            BookingStatusType.ACCEPTED -> {
                holder.acceptBtn.isVisible = false
                holder.removeBtn.isVisible = false
                "Godkänd bokning".also { holder.requestType.text = it }
            } else -> false
        }

        holder.removeBtn.setOnClickListener {
            remove(bookings.bookingId, bookings.userId)
        }
        holder.acceptBtn.setOnClickListener {
            add(bookings.bookingId, bookings.userId)
        }

    }

    override fun getItemCount(): Int {
        return BookingResponse.size
    }
}