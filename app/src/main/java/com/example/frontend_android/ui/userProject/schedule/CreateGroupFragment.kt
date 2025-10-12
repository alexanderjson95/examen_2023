package com.example.frontend_android.ui.userProject.schedule

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.ui.userProject.bookingInvites.ProjectBookingsViewmodel
import com.example.frontend_android.ui.userProject.projectInvites.UserProjectRequestViewmodel

import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import kotlin.getValue


// TODO:

/*
        1. gör så man kan ta bort förfrågan
        2. gör en mall av adapter + detta fragment för user till project
        3. på projects: lägg en knapp för att visa förfrågningar användare gjort, en för att visa förfrågningar, en för att visa alla andra - samma mall som ovan
        4. skapa enkel meddelande skärm igen (gamla)
        5. lägg på patch och remove på allt nu - inklusive user, project och user project (acceptera knapp)
        6. lägg på grundläggande kryptering

 */
@AndroidEntryPoint
class CreateGroupFragment : Fragment(R.layout.create_group_dialog) {
    private val bvm: ProjectBookingsViewmodel by activityViewModels()

    private val groupMembers = mutableSetOf<Long>()
    private var projectId: Long = 0L
    private val args: CreateGroupFragmentArgs by navArgs()

    private var showMembers: Boolean = false;

    private var startHour = 0
    private var startMinute = 0
    private var endHour = 0
    private var endMinute = 0
    private var dateMillis = 0L
    private var dateText = ""
    private var timeText = ""
    private lateinit var adapter: CreateGroupAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.messagesRecyclerView)

        val timeEditText = view.findViewById<TextInputEditText>(R.id.timeEditText)
        val timeInputLayout = view.findViewById<TextInputLayout>(R.id.timeInputLayout)


        val add_btn = view.findViewById<MaterialButton>(R.id.add_btn)
        val close_btn = view.findViewById<MaterialButton>(R.id.close_btn)
        val loggedInUserId = bvm.getId()
        groupMembers.add(loggedInUserId)
        projectId = args.projectId
        dateMillis = args.selectedDate
//        pvm.getUserProjects(projectId)
        bvm.getAvailableMembers(projectId,dateMillis)
        adapter = CreateGroupAdapter(
            addUser = { userId -> groupMembers.add(userId)
                groupMembers.add(userId)
                adapter.updateId(groupMembers)
                      },
            removeUser = {
                    userId -> groupMembers.remove(userId)
                groupMembers.remove(userId)
                adapter.updateId(groupMembers)
            }
        )
        lifecycleScope.launch {
            bvm.available.collect { p ->
                val filtered = p.filter { it.userId != loggedInUserId }
                adapter.submitList(filtered)
            }
        }

        lifecycleScope.launch {
            bvm.stateAdd.collect { p ->
                if(p==true){
                    Toast.makeText(requireContext(), "Bokning skapad!", Toast.LENGTH_SHORT).show()
                    val action = CreateGroupFragmentDirections.groupFragToProjectbookings(projectId)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Bokningen kunde inte skapas!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val cal = Calendar.getInstance().apply { timeInMillis = dateMillis }
        dateText = "${cal.get(Calendar.MONTH)}/${cal.get(Calendar.DAY_OF_MONTH)}"


        parentFragmentManager.setFragmentResultListener(
            "booking_request",
            viewLifecycleOwner
        ) { _, bundle ->
            dateMillis = bundle.getLong("dateMillis")
            startHour = bundle.getInt("startHour")
            startMinute = bundle.getInt("startMinute")
            endHour = bundle.getInt("endHour")
            endMinute = bundle.getInt("endMinute")
            timeText =
                String.format("%02d:%02d - %02d:%02d", startHour, startMinute, endHour, endMinute)
            timeInputLayout.setHint("$dateText $timeText")

        }


        close_btn.setOnClickListener {
            findNavController().popBackStack()
        }


        timeEditText.setOnClickListener {
            val startPicker = TimePickerDialog(
                requireContext(),
                { _, sHour, sMin ->
                    startHour = sHour
                    startMinute = sMin
                    val endPicker = TimePickerDialog(
                        requireContext(),
                        { _, eHour, eMin ->
                            endHour = eHour
                            endMinute = eMin
                        },
                        12, 0, true)
                        endPicker.show()

                },
                12, 0, true)
            startPicker.show()






            add_btn.setOnClickListener {
            bvm.addBooking(
                projectId = projectId,
                users = groupMembers.toList(),
                startHour = startHour,
                startMinute = startMinute,
                endHour = endHour,
                endMinute = endMinute,
                dateMillis = dateMillis,
                bookingTitle = dateMillis.toString()
            )
        }

    }
}
}

//
//
//    /**
//     *  Helper funktion för att filtera ut data. Fungerar endast i scenariot
//     *  där vi har två ordnade listor där lista B är större än A.
//     *
//     *  Denna är skapad för att kunna filtrera ID nummer där lista A (small list)
//     *  är en hämtad SQL tabell med foreign key i lista B. Exempel är om vi
//     *  har användare i ett projekt (där users = FK till Users) och en lista
//     *  med alla användare. Då skapar vi två bitset i storlek av users.
//     *  I vardera lista fyller vi varsitt BitSet och jämnför index istället för heltal.
//     *  Så om userID finns i userprojects och i users, då är den 1 i båda listorna.
//     *
//     *
//     *  Users bitset kommer vara en lista med bara 1:or, och members bitset
//     *  en lista med 1:or och 0:or. Så vi kan nu kolla på index, om t.ex index 17
//     *  och 1 på båda listona, då är id 17 både en medlem i projektet och en user.
//     *
//     */
//    private fun filterData(smallList: List<Long>, bigList: List<Long>, include: Boolean) : List<Long> {
//        val bigBits = BitSet(bigList.size)
//        val smallBits = BitSet(bigList.size)
//        bigList.forEach { bigBits.set(it.toInt()) }
//        smallList.forEach { smallBits.set(it.toInt()) }
//        val result = bigBits.clone() as BitSet
//        //tar endast fram gemensamma ID
//        if (include) {
//            result.and(smallBits)
//        }
//        else {
//            // tar endast fram exkluderade ID
//            result.andNot(smallBits)
//        }
//        val output =  result.stream().mapToObj { it.toLong() }.toList()
//        Log.d("InviteUserFragment: ", "showing members: $include and number of members: $output")
//        return output
//    }
//
//}
//



///**
// *  Förklaring:
// *  Vi vill hämta antingen medlemmar eller icke medlemmar.
// *   Istället för en linjär sökning (då dataset kan vara mycket högre)
// *
// *  1. Tar ut ID från memberList (behöver endast ID för en diff)
// *  2. Konverterar ID till hashset:
// *
// *
// *
// *  3. beroende på showMembers så returneras antingen members eller non members
// *
// *
// */
//private fun filterMembers(userList: List<UserResponse>, memberList: List<UserProjectResponse>): List<UserResponse> {
//    val membersIds = memberList.mapNotNull { it.userId }.toHashSet()
//    return if (showMembers) {
//        userList.filter { it.id in membersIds }
//    } else {
//        userList.filter { it.id !in membersIds }
//    }
//}


//    private fun observeViewModel(){
//        bvm.users.observe(viewLifecycleOwner){
//                users ->
//                    val members = bvm.members.value
//                    if (users != null && members != null){
//                        val memberIds = members.mapNotNull { it.userId }.toSet()
//                        adapter.updateId(memberIds)
//                    adapter.submitList(filterMembers(users,members))
//                        Log.d("InviteUserFragment", "Fetched users: ${users.map { it.id }}")
//                }
//            }
//        bvm.members.observe(viewLifecycleOwner) { members ->
//                val users = bvm.users.value
//                if (users != null && members != null){
//                    val memberIds = members.mapNotNull { it.userId }.toSet()
//                    adapter.updateId(memberIds)
//                    adapter.submitList(filterMembers(users,members))
//                    Log.d("InviteUserFragment", "Fetched users: ${users.map { it.id }}")
//            }
//        }
//    }


