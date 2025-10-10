package com.example.frontend_android.ui.userProject.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.example.frontend_android.ui.userProject.projectDashboard.MyProjectFragmentArgs
import com.example.frontend_android.ui.userProject.projectDashboard.MyProjectViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MyProjectSettings : Fragment(R.layout.fragment_my_project_settings){

    private var userId: Long = 0L
    private val args: MyProjectFragmentArgs by navArgs()
    private val vm: MyProjectSettingsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId


        val destroy_btn = view.findViewById<MaterialButton>(R.id.destroy_btn)
        val leaveBtn = view.findViewById<MaterialButton>(R.id.leave_btn)




        destroy_btn.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Bekräfta borttagning")
                    .setMessage("Vill du verkligen ta bort projektet?")
                    .setPositiveButton("TA BORT") { _, _ ->
                        vm.deleteProject(projectId)
                        findNavController().popBackStack(R.id.navigation_dashboard, false)
                    }
                    .setNegativeButton("Avbryt", null)
                    .show()
        }


        leaveBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Bekräfta borttagning")
                .setMessage("Vill du verkligen lämna projektet?")
                .setPositiveButton("TA BORT") { _, _ ->
                    vm.deleteUserProject(projectId)
                    findNavController().popBackStack(R.id.navigation_dashboard, false)
                }
                .setNegativeButton("Avbryt", null)
                .show()
        }

    }

}