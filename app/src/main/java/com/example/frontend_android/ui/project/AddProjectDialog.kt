package com.example.frontend_android.ui.project

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.frontend_android.R
import com.example.frontend_android.ui.dashboard.DashboardFragmentDirections
import com.example.frontend_android.ui.dashboard.DashboardViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AddProjectDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_project_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val projectName = view.findViewById<EditText>(R.id.projectName)
        val projectDescription = view.findViewById<EditText>(R.id.description_key)
        val addBtn = view.findViewById<MaterialButton>(R.id.create_project_btn)
        val closeBtn = view.findViewById<MaterialButton>(R.id.close_dialog_btn)
        val inputs = listOf<EditText>(projectName, projectDescription)
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnRequests -> {
                        val action = AddProjectDialogDirections.dashToUserProject()
                        findNavController().navigate(action)
                    }
                }
            }
        }


        addBtn.setOnClickListener {
            if (!checkInputs(inputs)) {
                Toast.makeText(requireContext(), "GÖR OM GÖR RÄTT!", Toast.LENGTH_SHORT).show()
            }
            else {
                val result = Bundle().apply {
                    putString("projectName", projectName.text.toString())
                    putString("projectDescription", projectDescription.text.toString())
                }
                parentFragmentManager.setFragmentResult("addProjectRequest", result)
                val action = AddProjectDialogDirections.dashToUserProject()
                findNavController().navigate(action)
            }

            closeBtn.setOnClickListener { dismiss() }
            isCancelable = true
        }
    }


    fun checkInputs(list: List<EditText>): Boolean {
        for (i in list){
            if(i.text.isNullOrBlank()) return false
        }
        return true
    }
}
