package com.example.frontend_android.ui.userProject.schedule

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
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
import androidx.compose.material.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.BookingStatusType
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.ui.userProject.projectDashboard.MyProjectFragmentArgs
import com.example.frontend_android.ui.userProject.projectInvites.UserProjectRequestViewmodel
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import kotlin.getValue
import androidx.core.graphics.toColorInt
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.Configuration
import com.example.frontend_android.ui.userProject.bookingInvites.ProjectBookingsViewmodel
import com.prolificinteractive.materialcalendarview.CalendarMode
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProjectBookings : Fragment(R.layout.fragment_booking) {

    private val upVM: ProjectBookingsViewmodel by viewModels()

    private val bookedMap = mutableMapOf<CalendarDay, List<BookingResponse>>()
    private var userId: Long = 0L
    private val args: ProjectBookingsArgs by navArgs()

    private var bookingId: Long = 0L
    private var userBookingId: Long = 0L



    private var selectedDate: Long = System.currentTimeMillis()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val calendarView = view.findViewById<MaterialCalendarView>(R.id.calendarView)
        val setTimeBtn = view.findViewById<MaterialButton>(R.id.setTimeBtn)
        val removeTimeBtn = view.findViewById<MaterialButton>(R.id.removeTimeBtn)

        setTimeBtn.isEnabled = false
        super.onViewCreated(view, savedInstanceState)

        val loggedInUserId = upVM.getId()

        val projectId = args.projectId


        upVM.getBooking(loggedInUserId)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                upVM.bookings.collectLatest { b ->
                    calendarView.removeDecorators()
                    bookedMap.clear()

                    val projectBookings = b.filter { it.projectId == args.projectId }
                    if (projectBookings.isNotEmpty()) {
                        projectBookings.map { booking ->
                            booking.dateMillis.let { m ->
                                val localDate = Instant.ofEpochMilli(m)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                val day = CalendarDay.from(localDate)
                                bookedMap[day] = bookedMap.getOrDefault(day, emptyList()) + booking

                                val bookedDays =
                                    bookedMap.filter { it.value.any { booking -> booking.status == BookingStatusType.ACCEPTED } }
                                        .keys
                                        .toList()
                                val requestedDays =
                                    bookedMap.filter { it.value.any { booking -> booking.status == BookingStatusType.INVITE } }
                                        .keys
                                        .toList()
                                val adminDays =
                                    bookedMap.filter { it.value.any { booking -> booking.status == BookingStatusType.ADMIN } }
                                        .keys
                                        .toList()


                                if (bookedDays.isNotEmpty()) {
                                    calendarView.addDecorator(ScheduleDecorator(bookedDays, Color.RED))
                                }

                                if (requestedDays.isNotEmpty()) {
                                    calendarView.addDecorator(ScheduleDecorator(requestedDays, Color.YELLOW))
                                }
                                if (adminDays.isNotEmpty()) {
                                    calendarView.addDecorator(ScheduleDecorator(adminDays, Color.BLUE))
                                }
                            }
                        }
                    }
                }

            }
        }

        val orientation = resources.configuration.orientation
        if (orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE){
            calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit()
        }
        else if (orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT){
            calendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit()
        }


//        upVM.getUserProjects(projectId)

        lifecycleScope.launch {

                calendarView.setOnDateChangedListener { w, d, s ->
                    val calendar = Calendar.getInstance().apply {
                        set(d.year, d.month - 1, d.day, 0, 0, 0)
                    }
                    selectedDate = calendar.timeInMillis


                    val bookingsOnDay = bookedMap[d]
                    if (bookingsOnDay.isNullOrEmpty()){
                        setTimeBtn.isEnabled = true
                    } else {
                        val status = bookingsOnDay.map { it.status }.distinct()
                        bookingId = bookingsOnDay.single().bookingId

                        when {
                            BookingStatusType.AVAILABLE in status ->{
                                setTimeBtn.isEnabled = true
                            }

                            BookingStatusType.ACCEPTED in status -> {
                                setTimeBtn.isEnabled = false
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Redan bokad")
                                    .setNegativeButton("Avbryt",null)
                                    .show()
                                val booking = bookingsOnDay.first()
                                bookingId = bookingsOnDay.single().bookingId

                                val infoText = """
                                    Start: ${booking.startHour} : ${booking.startMinute}
                                    Slut: ${booking.endHour} : ${booking.endMinute}
                                """.trimIndent()
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Bokningsinfo")
                                    .setMessage(infoText)
                                    .setNegativeButton("Stäng", null)
                                    .show()
                            }


                            BookingStatusType.INVITE in status ->{
                                Toast.makeText(requireContext(), "Den här dagen har redan en aktiv inbjudan",Toast.LENGTH_SHORT).show()
                                setTimeBtn.isEnabled = false
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Ingen bokning")
                                    .setNegativeButton("Avbryt",null)
                                    .show()
                            }
                        }
                    }
                }

                setTimeBtn.setOnClickListener {
                    if (selectedDate >= 1L) {
                        val action = ProjectBookingsDirections
                            .actionProjectBookingsToCreateBookingFragment(projectId, selectedDate)
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Välj ett datum först!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }



                removeTimeBtn.setOnClickListener {
                    if (selectedDate >= 1L ) {
                        upVM.removeUserBooking(bookingId,loggedInUserId);
                    }
                }
            }
        }
    }


class ScheduleDecorator(
    private val dates: Collection<CalendarDay>,
    private val color: Int,
) :
    DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(22f, color))

    }
}