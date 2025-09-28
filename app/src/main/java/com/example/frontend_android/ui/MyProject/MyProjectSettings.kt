package com.example.frontend_android.ui.MyProject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.example.frontend_android.ui.user.UsersViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MyProjectSettings : Fragment(R.layout.fragment_my_project_settings){

    private var userId: Long = 0L
    private val args: MyProjectFragmentArgs by navArgs()
    private val vm: MyProjectViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        vm.getUser()

        vm.user.observe(viewLifecycleOwner) { u ->
            userId = u?.id ?: 0
            vm.getRoless(userId)
        }

        val adminBtn = view.findViewById<MaterialButton>(R.id.admin_btn)
        val leaveBtn = view.findViewById<MaterialButton>(R.id.leave_btn)

        adminBtn.setOnClickListener {
            val action = MyProjectSettingsDirections
                .dashToUpAdminSettings(projectId, userId)
            findNavController().navigate(action)
        }
        leaveBtn.setOnClickListener {}

    }

}