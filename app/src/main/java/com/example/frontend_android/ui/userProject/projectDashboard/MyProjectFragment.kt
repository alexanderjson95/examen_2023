package com.example.frontend_android.ui.userProject.projectDashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MyProjectFragment : Fragment(R.layout.fragment_my_project){

    private var userId: Long = 0L
    private val args: MyProjectFragmentArgs by navArgs()
    private val vm: MyProjectViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        vm.getLoggedInUserProject(projectId)

        vm.userProject.observe(viewLifecycleOwner) { u ->
            Toast.makeText(requireContext(), "Admin result: ${u.firstName}  is:  ${u.isAdmin}", Toast.LENGTH_LONG)
                .show()
        }


        vm.admin.observe(viewLifecycleOwner) { u ->
            Toast.makeText(requireContext(), "Admin result: $u", Toast.LENGTH_LONG)
                .show()
        }


            vm.roless.observe(viewLifecycleOwner) { roles ->
            roles.forEach { role ->
                println("ROLES: $role")
            }
        }

        val settings_btn = view.findViewById<MaterialButton>(R.id.settings_btn)
        val members_btn = view.findViewById<MaterialButton>(R.id.members_btn)
        val invite_btn = view.findViewById<MaterialButton>(R.id.message_btn)
        val schedule_bt = view.findViewById<MaterialButton>(R.id.schedule_btn)

        settings_btn.setOnClickListener {
            val action = MyProjectFragmentDirections
                .dashToUpSettings(projectId, userId)
            findNavController().navigate(action) }

        schedule_bt.setOnClickListener {
            val action = MyProjectFragmentDirections
                .myProjectToBookings(projectId)
            findNavController().navigate(action) }

        invite_btn.setOnClickListener {
            val action = MyProjectFragmentDirections
                .myProjectToInvite(projectId)
            findNavController().navigate(action) }


        members_btn.setOnClickListener {
            val action = MyProjectFragmentDirections
                .myProjectToMembers(projectId)
            findNavController().navigate(action) }

    }

}