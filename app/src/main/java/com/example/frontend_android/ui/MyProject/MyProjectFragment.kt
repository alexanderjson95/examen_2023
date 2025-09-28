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
class MyProjectFragment : Fragment(R.layout.fragment_my_project){

    private var userId: Long = 0L
    private val args: MyProjectFragmentArgs by navArgs()
    private val vm: MyProjectViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        Toast.makeText(requireContext(), "ENTERING : $projectId", Toast.LENGTH_SHORT).show()

        vm.getUser()
        vm.user.observe(viewLifecycleOwner) { u ->
            userId = u?.id ?: 0
            Toast.makeText(requireContext(), "userid : $userId", Toast.LENGTH_SHORT).show()
            vm.getRoless(userId)
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
                .myProjectToBookings(projectId,userId)
            findNavController().navigate(action) }

        members_btn.setOnClickListener {
            val action = MyProjectFragmentDirections
                .myProjectToMembers(projectId, userId)
            findNavController().navigate(action) }

    }

}