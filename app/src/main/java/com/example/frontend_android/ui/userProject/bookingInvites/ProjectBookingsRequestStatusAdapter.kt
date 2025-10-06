package com.example.frontend_android.ui.userProject.bookingInvites

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
import com.example.frontend_android.ui.dashboard.invites.bookingInvites.UserBookingsRequestStatusAdapter
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneId.systemDefault

@FragmentScoped
class ProjectBookingsRequestStatusAdapter @Inject constructor(private val accept: (Long) -> Unit, private val remove: (Long) -> Unit):
    RecyclerView.Adapter<ProjectBookingsRequestStatusAdapter.UserBookingsRequestViewholder>(){
    private var BookingResponse: List<BookingResponse> = emptyList()
    class UserBookingsRequestViewholder(view: View) : RecyclerView.ViewHolder(view){
        var name_value: TextView = view.findViewById(R.id.name_value)
        var date: TextView = view.findViewById(R.id.date)
        var lname_value: TextView = view.findViewById(R.id.lname_value)

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
            .inflate(R.layout.item_bookings_request_alt, parent, false)
        return UserBookingsRequestViewholder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: ProjectBookingsRequestStatusAdapter.UserBookingsRequestViewholder,
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


        val bList = listOf(bookings.status == BookingStatusType.AVAILABLE,BookingStatusType.ACCEPTED)

        when(bList){
            listOf(true, true) ->
            {
                holder.removeBtn.isVisible = false
                holder.acceptBtn.isVisible = false
                holder.requestType.text = "Inbokad"

            }

            listOf(true, false) ->
            {
                holder.removeBtn.isVisible = true
                holder.acceptBtn.isVisible = false
                holder.requestType.text = "Kan jobba"
            }

            listOf(false, true) ->
            {
                holder.removeBtn.isVisible = true
                holder.acceptBtn.isVisible = true
                holder.requestType.text = "Bokningsförfrågan"
            } else -> {
            holder.removeBtn.isVisible = false
            holder.acceptBtn.isVisible = false
            holder.requestType.text = "Nekat"
        }

        }

        holder.removeBtn.setOnClickListener {
            remove(bookings.bookingId)
        }
        holder.acceptBtn.setOnClickListener {
            accept(bookings.bookingId)
        }

    }
    override fun getItemCount(): Int {
        return BookingResponse.size
    }
}