package com.example.frontend_android.ui.schedule

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import java.time.Instant
import java.time.ZoneId

class BookingDialog : DialogFragment() {
    private val args: BookingDialogArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.add_booking_form, null)
        val startBtn = view.findViewById<MaterialButton>(R.id.start_key)
        val endBtn = view.findViewById<MaterialButton>(R.id.end_key)
        var startHour: Int? = null
        var startMinute: Int? = null
        var endHour: Int? = null
        var endMinute: Int? = null

        val millis = args.dateMillis
        val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()

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
            .setTitle("Skapa bokning")
            .setMessage("Valt: $localDate")
            .setView(view)


            .setPositiveButton("OK") {dialog, _ ->
                if (timeCheck(startHour,startMinute,endHour,endMinute)){
                    Toast.makeText(requireContext(),"Bokning skickad ;)", Toast.LENGTH_LONG).show()
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
        fun newInstance(date: String): BookingDialog {
            val dialog = BookingDialog()
            val args = Bundle()
            args.putString("date", date)
            dialog.arguments = args
            return dialog
        }

    }

}
