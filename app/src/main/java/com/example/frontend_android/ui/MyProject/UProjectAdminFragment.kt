package com.example.frontend_android.ui.MyProject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import kotlin.getValue
import androidx.navigation.fragment.findNavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

class UProjectAdminFragment : Fragment(R.layout.fragment_up_admin) {

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

        val add_members_btn = view.findViewById<MaterialButton>(R.id.invite_user_btn)
        val show_requests_btn = view.findViewById<MaterialButton>(R.id.handle_requests_btn)
        val edit_members_btn = view.findViewById<MaterialButton>(R.id.edit_member_btn)
        val delete_project_btn = view.findViewById<MaterialButton>(R.id.delete_project_btn)



        add_members_btn.setOnClickListener {
            val action = UProjectAdminFragmentDirections
                .dashToAddUser(projectId, userId)
            findNavController().navigate(action)
        }

        show_requests_btn.setOnClickListener {
            val action = UProjectAdminFragmentDirections.dashToProjectReqs(projectId, userId)
            findNavController().navigate(action)
        }
//        edit_members_btn.setOnClickListener {
//            val action = MyProjectSettingsDirections
//                .dashToEditMember(projectId, userId)
//            findNavController().navigate(action)
//        }
        delete_project_btn.setOnClickListener {

        }
    }
}
