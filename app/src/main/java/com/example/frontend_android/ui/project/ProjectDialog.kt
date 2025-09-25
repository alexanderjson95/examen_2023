package com.example.frontend_android.ui.project

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.example.frontend_android.ui.schedule.BookingDialogArgs
import com.google.android.material.button.MaterialButton
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.getValue

class ProjectDialog : DialogFragment() {
    private val args: BookingDialogArgs by navArgs()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.add_project_form, null)

        val projectName = view.findViewById<EditText>(R.id.projectName)
        val projectDescription = view.findViewById<EditText>(R.id.description_key)
        val addBtn = view.findViewById<MaterialButton>(R.id.create_project_btn)
        val closeBtn = view.findViewById<MaterialButton>(R.id.close_dialog_btn)

        addBtn.setOnClickListener {

        }

        closeBtn.setOnClickListener {

        }


        return AlertDialog.Builder(requireContext())
            .setTitle("dfsdfsdfsd")
            .setMessage("Datumsdfsdf")
            .setView(view)


            .setPositiveButton("OK") {dialog, _ ->

                dialog.dismiss()}
            .create()
    }


    companion object {
        fun newInstance(date: String, project:String): ProjectDialog {
            val dialog = ProjectDialog()
            val args = Bundle()
            args.putString("date", date)
            args.putString("project", project)
            dialog.arguments = args
            return dialog
        }

    }

}
