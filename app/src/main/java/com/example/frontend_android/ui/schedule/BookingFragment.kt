package com.example.frontend_android.ui.schedule

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.frontend_android.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import dagger.hilt.android.AndroidEntryPoint
import java.time.ZoneId

@AndroidEntryPoint
class BookingFragment : Fragment(R.layout.fragment_booking){


    private lateinit var calendarView: MaterialCalendarView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        calendarView = view.findViewById(R.id.calendarView)
        calendarView.setCurrentDate(CalendarDay.today())


        calendarView.setOnDateChangedListener { _, date: CalendarDay, _ ->
            val millis = date.date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val action = BookingFragmentDirections.actionBookingFragmentToBookingDialog(
                dateMillis = millis
            )
            findNavController().navigate(action)
        }

        }
    }
