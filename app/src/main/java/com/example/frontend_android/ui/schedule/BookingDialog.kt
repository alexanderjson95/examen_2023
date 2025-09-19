package com.example.frontend_android.ui.schedule

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.example.frontend_android.ui.project.ProjectsViewModel
import com.example.frontend_android.ui.user.UsersViewModel
import com.google.android.material.button.MaterialButton
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.getValue

class BookingDialog : DialogFragment() {
    private val args: BookingDialogArgs by navArgs()
    @SuppressLint("UseGetLayoutInflater")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.add_booking_form, null)
        val startBtn = view.findViewById<MaterialButton>(R.id.start_key)
        val endBtn = view.findViewById<MaterialButton>(R.id.end_key)
        val millis = args.dateMillis
        val selectedProject = args.selectedProject
        val selectedMember = args.selectedUser
        var startHour: Int? = args.startHour
        var startMinute: Int? = args.startMin
        var endHour: Int? = args.endHour
        var endMinute: Int? = args.endMin
        val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
        val today = LocalDate.now()
        startBtn.text = String.format("Start: %02d:%02d", startHour, startMinute)
        endBtn.text = String.format("Slut: %02d:%02d", endHour, endMinute)
        startBtn.setOnClickListener {
            val timePickerStart = TimePickerDialog(requireContext(),
                { _, hourOfDay, minute ->
                    startHour = hourOfDay
                    startMinute = minute
                    startBtn.text = String.format("Start: %02d:%02d", startHour, startMinute)
                },12,0,true
            )
            timePickerStart.show()
        }

        endBtn.setOnClickListener {
            val timePickerEnd = TimePickerDialog(requireContext(),
                { _, hourOfDay, minute ->
                    endHour = hourOfDay
                    endMinute = minute
                    endBtn.text = String.format("Slut: %02d:%02d", endHour, endMinute)
                },
                12,0,true
            )
            timePickerEnd.show()
        }
        return AlertDialog.Builder(requireContext())
            .setTitle("Bokning för: $selectedMember Projekt: $selectedProject")
            .setMessage("Datum: $localDate")
            .setView(view)
            .setPositiveButton("OK") {dialog, _ ->
                if (localDate.isBefore(today)){
                    Toast.makeText(requireContext(),"Bokning kan inte göras i dåtid", Toast.LENGTH_LONG).show()
                }
                else if (timeCheck(startHour,startMinute,endHour,endMinute)){
                    Toast.makeText(requireContext(),"Bokning skickad", Toast.LENGTH_LONG).show()
                    val result = Bundle().apply {
                        putLong("dateMillis", millis)
                        putString("selectedProject", selectedProject)
                        putString("selectedUser", selectedMember)
                        putInt("startHour", startHour!!)
                        putInt("startMinute", startMinute!!)
                        putInt("endHour", endHour!!)
                        putInt("endMinute", endMinute!!)
                    }
                    parentFragmentManager.setFragmentResult("booking_request", result)
                } else {
                    Toast.makeText(requireContext(),"Ingen bokning gjort! Sluttid kan inte vara innan start!", Toast.LENGTH_LONG).show()
                }
                dialog.dismiss()}
            .create()
    }
    fun timeCheck(startHour: Int?, startMin: Int?, endHour: Int?, endMin: Int?): Boolean {
        // Kollar först så alla tider är ifyllda genom boolean check
        val nums = listOf(startHour,startMin,endHour,endMin)
        if (nums.filterNotNull().size != 4){
            Toast.makeText(requireContext(),"Alla tider måste vara ifyllda...!", Toast.LENGTH_LONG).show()
            return false
        }
        // deklarerar att start alltid är mindre än slut, annars false
       return startHour!! * 60+ startMin!! < endHour!! * 60 + endMin!!
    }
    companion object {
        fun newInstance(date: String, project:String): BookingDialog {
            val dialog = BookingDialog()
            val args = Bundle()
            args.putString("date", date)
            args.putString("project", project)
            dialog.arguments = args
            return dialog
        }

    }

}
