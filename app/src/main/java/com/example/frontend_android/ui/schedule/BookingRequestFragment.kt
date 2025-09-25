package com.example.frontend_android.ui.schedule

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.ui.user.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class BookingRequestFragment : Fragment(R.layout.fragment_my_project_requests){
    private val vm: UsersViewModel by activityViewModels()
    private val bookingVM: BookingsViewModel by activityViewModels()
    private var projectId: Long? = null

    private var memberId: Long? = null

    private var isAdmin: Boolean = false

    private var userId: Long = 0L


    private lateinit var adapter: MemberAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view.findViewById<RecyclerView>(R.id.projectRequestRecycler)


        adapter = MemberAdapter { memberId ->

            if (isAdmin && memberId != userId) {
                Toast.makeText(requireContext(), "Member: ${memberId} is being kicked!!!!", Toast.LENGTH_SHORT).show()
            }

            else if (memberId == userId) {
                Toast.makeText(requireContext(), "Member: ${memberId} leaving!!!!!!!", Toast.LENGTH_SHORT).show()
            }

            else
            {
                Toast.makeText(requireContext(), "Member: ${memberId} is NOT being kicked!!!!", Toast.LENGTH_SHORT).show()
            }


        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        projectId?.let { bookingVM.getMember(projectId!!) }

        bookingVM.getUser()



        bookingVM.user.observe(viewLifecycleOwner) { u ->
            userId = u?.id ?: 0
            Toast.makeText(requireContext(), "userid : $userId", Toast.LENGTH_SHORT).show()
            bookingVM.getRoless(userId)
        }



        bookingVM.members.observe(viewLifecycleOwner) { members ->
            adapter.submitList(members)
            val adminUser = userId
            isAdmin = members.any { it.userId == adminUser && it.isAdmin == true }
            Toast.makeText(requireContext(), "Member: $isAdmin", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        adapter.submitList(emptyList())
        bookingVM.clearAll()
    }
}
