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
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
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
class BookingFragment : Fragment(R.layout.fragment_booking) {


    private lateinit var calendarView: MaterialCalendarView
    private val bookingVM: BookingsViewModel by activityViewModels()
    private lateinit var selectedProject: String
    private lateinit var selectedUser: String

    private var projectId: Long? = null
    private var userId: Long? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        calendarView = view.findViewById(R.id.calendarView)
        calendarView.setCurrentDate(CalendarDay.today())
        val projectSpinner = view.findViewById<Spinner>(R.id.userprojectSpinner)
        val memberSpinner = view.findViewById<Spinner>(R.id.memberSpinner)
         var projectsList: List<UserProjectResponse>? = null
         var membersList: List<UserProjectResponse>? = null

        bookingVM.getAllProjects()
        bookingVM.projects.observe(viewLifecycleOwner) { projects ->
            if (!projects.isNullOrEmpty()) {
                projectsList = projects
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    projectsList.map { it.projectName }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                projectSpinner.adapter = adapter

                projectSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedProject = projectsList[position].projectName.toString()
                            projectId = projectsList[position].projectId
                            calendarView.removeDecorators()


                            Toast.makeText(
                                requireContext(),
                                "Valt projekt: $selectedProject",
                                Toast.LENGTH_SHORT
                            ).show()
                            bookingVM.getMember(projectId!!)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }

            }


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
            parentFragmentManager.setFragmentResultListener("booking_request", viewLifecycleOwner) { _, bundle ->
                val dateMillis = bundle.getLong("dateMillis")
                val startHour = bundle.getInt("startHour")
                val startMinute = bundle.getInt("startMinute")
                val endHour = bundle.getInt("endHour")
                val endMinute = bundle.getInt("endMinute")
                bookingVM.addBooking(projectId, userId,startHour,startMinute,endHour,endMinute,dateMillis)
            }
        }
        calendarView.setOnDateChangedListener { _, date: CalendarDay, _ ->
            if (!::selectedProject.isInitialized || !::selectedUser.isInitialized) {
                Toast.makeText(
                    requireContext(),
                    "Välj projekt och användare först",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnDateChangedListener
            }
                    val millis =
                        date.date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    val action = BookingFragmentDirections.actionBookingFragmentToBookingDialog(
                        dateMillis = millis,
                        selectedProject = selectedProject,
                        selectedUser = selectedUser
                    )
                    findNavController().navigate(action)
                }

            }

        bookingVM.bookings.observe(viewLifecycleOwner) { b ->
            if (!b.isNullOrEmpty()) {
                val bookedDates = b.map { booking ->
                    booking.dateMillis.let { m ->
                        val localDate = Instant.ofEpochMilli(m)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        CalendarDay.from(localDate)
                    }
                }
                calendarView.removeDecorators()
                calendarView.addDecorator(ScheduleDecorator(bookedDates, Color.RED))
            }
        }
    }
}



class ScheduleDecorator(private val dates: Collection<CalendarDay>, private val color: Int)
    : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(10f, color))
    }

    fun rotateColors(iterations: Int){
        var r = 0
        var g = 0
        var b = 0
        val max = 255
        val colorList = mutableListOf<Int>()
        for (i in 0..iterations){
            r = listOf(0,100,255).random()
            g = listOf(0,100,255).random()
            b =listOf(0,100,255).random()
            colorSwapper(r,g,b)
        }
    }

    fun colorSwapper(r: Int, g: Int, b: Int): Triple<Int, Int, Int> {
        return Triple(r,g,b)
    }
}

//            bookingVM.bookings.observe(viewLifecycleOwner){
//                    bookings ->
//                if (bookings != null){
//                    bookingList = bookings
//                }
//            }


