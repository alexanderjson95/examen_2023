package com.example.frontend_android.ui.schedule

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.google.android.material.button.MaterialButton
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId
import kotlin.getValue

@AndroidEntryPoint
class MyBookingFragment : Fragment(R.layout.fragment_booking) {
    private lateinit var calendarView: MaterialCalendarView
    private val bookingVM: BookingsViewModel by activityViewModels()
    private lateinit var selectedUser: String
    private lateinit var selectedBooking: BookingResponse
    private val args: MyBookingFragmentArgs by navArgs()
    private val bookedMap = mutableMapOf<CalendarDay, List<BookingResponse>>()
    private var projectId: Long? = null
    private var userId: Long? = null
    private var bookingId: Long? = null

    // Default att man sätter upp tillänglig tid, annars false = bokning
    private var availability: Boolean = true
    private var isBooked: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        projectId = args.projectId
        bookingVM.getUser()
        bookingVM.user.observe(viewLifecycleOwner) { u -> userId = u?.id }
        bookingVM.getBooking(userId)
        bookingVM.bookings.observe(viewLifecycleOwner) { b ->
            calendarView.removeDecorators()
            bookedMap.clear()
            if (!b.isNullOrEmpty()) {
                val bookedDates = b.map { booking ->
                    booking.dateMillis.let { m ->
                        val localDate = Instant.ofEpochMilli(m)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val day = CalendarDay.from(localDate)
                        bookedMap[day] = bookedMap.getOrDefault(day, emptyList()) + booking
                        val availableDays =
                            bookedMap.filter { it.value.any { booking -> booking.availability } }
                                .keys
                                .toList()
                        val bookedDays =
                            bookedMap.filter { it.value.any { booking -> !booking.availability } }
                                .keys
                                .toList()
                        val requestedDays =
                            bookedMap.filter { it.value.any { booking -> !booking.accepted && !booking.availability } }
                                .keys
                                .toList()

                        if (availableDays.isNotEmpty()) {
                                calendarView.addDecorator(ScheduleDecorator(availableDays, Color.GREEN))
                            }

                        if (bookedDays.isNotEmpty()) {
                            calendarView.addDecorator(ScheduleDecorator(bookedDays, Color.RED))
                            }

                        if (requestedDays.isNotEmpty()) {
                            calendarView.addDecorator(ScheduleDecorator(requestedDays, Color.YELLOW))
                            }
                        }
                    }
                } }

            val availableBtn = view.findViewById<MaterialButton>(R.id.available_btn)
            val bookBtn = view.findViewById<MaterialButton>(R.id.booking_btn)


            val memberSpinner = view.findViewById<Spinner>(R.id.memberSpinner)
            var membersList: List<UserProjectResponse>? = null


            memberSpinner.visibility = View.INVISIBLE
            bookBtn.setOnClickListener {
                availability = false
                memberSpinner.visibility = View.VISIBLE
            }
            availableBtn.setOnClickListener {
                availability = true
                memberSpinner.visibility = View.INVISIBLE
            }

            calendarView = view.findViewById(R.id.calendarView)
            calendarView.setCurrentDate(CalendarDay.today())
            bookingVM.getMember(projectId!!)
            bookingVM.members.observe(viewLifecycleOwner) { members ->
                if (!members.isNullOrEmpty()) {
                    membersList = members

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        membersList.map { it.username }
                    )

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    memberSpinner.adapter = adapter


                    memberSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                selectedUser = membersList[position].username.toString()
                                userId = membersList[position].userId
                                bookingVM.getBooking(userId)
                                Toast.makeText(
                                    requireContext(),
                                    "Vald användare: $selectedUser",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }
                }




                calendarView.setOnDateChangedListener { _, date: CalendarDay, _ ->
                    if (!::selectedUser.isInitialized) {
                        Toast.makeText(
                            requireContext(),
                            "Välj projekt och användare först",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnDateChangedListener
                    }

                    val bookingsForDay = bookedMap[date]
                    if (!bookingsForDay.isNullOrEmpty()) {
                        isBooked = true
                        bookingId = bookingsForDay.firstOrNull()?.bookingId

                        Toast.makeText(
                            requireContext(),
                            "booking id: $bookingId",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        isBooked = false
                        Toast.makeText(requireContext(), "Bokning: $isBooked", Toast.LENGTH_SHORT)
                            .show()
                    }


                    val sHour = bookingsForDay?.map { it.startHour }
                    val sMin = bookingsForDay?.map { it.startMinute }
                    val eHour = bookingsForDay?.map { it.endHour }
                    val eMin = bookingsForDay?.map { it.endMinute }
                    val acception = bookingsForDay?.map { it.accepted }

                    val projectBooking = bookingsForDay?.map { it.projectName }
                    val bookingFormat =
                        "Project: $projectBooking - Time: $sHour:$sMin - $eHour:$eMin - Accepted: $acception"
                    Toast.makeText(
                        requireContext(),
                        "Vald bokning: $bookingFormat",
                        Toast.LENGTH_SHORT
                    ).show()

                    val millis =
                        date.date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    val action = MyBookingFragmentDirections.actionBookingFragmentToBookingDialog(
                        dateMillis = millis,
                        selectedUser = selectedUser,
                        selectedProject = "",
                        startHour = sHour?.first() ?: 0,
                        startMin = sMin?.first() ?: 0,
                        endHour = eHour?.first() ?: 0,
                        endMin = eMin?.first() ?: 0,
                    )
                    findNavController().navigate(action)
                }

                parentFragmentManager.setFragmentResultListener(
                    "booking_request",
                    viewLifecycleOwner
                ) { _, bundle ->
                    val dateMillis = bundle.getLong("dateMillis")
                    val startHour = bundle.getInt("startHour")
                    val startMinute = bundle.getInt("startMinute")
                    val endHour = bundle.getInt("endHour")
                    val endMinute = bundle.getInt("endMinute")
                    if (isBooked) {
                        bookingId?.let {
                            bookingVM.patchBooking(
                                it,
                                projectId,
                                userId,
                                startHour,
                                startMinute,
                                endHour,
                                endMinute,
                                availability,
                                dateMillis,
                                false
                            )
                        }
                    } else {
                        bookingVM.addBooking(
                            projectId,
                            userId,
                            startHour,
                            startMinute,
                            endHour,
                            endMinute,
                            availability,
                            dateMillis
                        )
                    }
                }
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
            view?.addSpan(DotSpan(10f, color))
        }
    }




