package com.example.frontend_android.ui.userProject.schedule

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.BookingStatusType
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.ui.userProject.projectDashboard.MyProjectFragmentArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import kotlin.getValue

@AndroidEntryPoint
class ProjectBookings : Fragment(R.layout.fragment_booking) {

    private val bookingVM: BookingsViewModel by activityViewModels()
    private lateinit var selectedUser: String
    private lateinit var selectedBooking: BookingResponse
    private val bookedMap = mutableMapOf<CalendarDay, List<BookingResponse>>()
    private  var userId: Long = 0L
    private val args: ProjectBookingsArgs by navArgs()

    private var bookingId: Long = 0L

    // Default att man sätter upp tillänglig tid, annars false = bokning
    private var availability: Boolean = true
    private var isBooked: Boolean = false

    private var selectedDate: Long = System.currentTimeMillis()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val calendarView = view.findViewById<MaterialCalendarView>(R.id.calendarView)
        val setTimeBtn = view.findViewById<MaterialButton>(R.id.setTimeBtn)

        super.onViewCreated(view, savedInstanceState)


        val projectId = args.projectId

        calendarView.setOnDateChangedListener   { w, d, s ->
            val calendar = Calendar.getInstance().apply {
                set(d.year, d.month - 1, d.day, 0, 0, 0)            }
            selectedDate = calendar.timeInMillis
        }

        setTimeBtn.setOnClickListener {
            if (selectedDate >= 1L){
                val action = ProjectBookingsDirections
                    .actionProjectBookingsToCreateBookingFragment(projectId,selectedDate)
                findNavController().navigate(action)
            } else{
                Toast.makeText(requireContext(), "Välj ett datum först!", Toast.LENGTH_SHORT).show()            }
            }



    }

}

class ScheduleDecorator(
    private val dates: Collection<CalendarDay>,
    private val color: Int
) :
    DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(22f, color))

    }
}
