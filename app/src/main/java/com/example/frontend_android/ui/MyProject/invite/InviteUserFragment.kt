package com.example.frontend_android.ui.MyProject.invite

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.schedule.BookingsViewModel
import com.example.frontend_android.ui.schedule.ProjectUserFragmentArgs
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.BitSet
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
class InviteUserFragment : Fragment(R.layout.fragment_my_project_adduser) {
    private val bvm: BookingsViewModel by activityViewModels()

    private var projectId: Long = 0L
    private val args: ProjectUserFragmentArgs by navArgs()

    private var showMembers: Boolean = false;

    private lateinit var adapter: InviteUserAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.usersAddRecyclerView)
        val checkBox = view.findViewById<CheckBox>(R.id.showMembersCheck)

        projectId = args.projectId

        bvm.users.observe(viewLifecycleOwner) { users ->
            val members = bvm.members.value
            if (users != null && members != null) {
                val memberIds = members.mapNotNull { it.userId }.toSet()
                adapter.updateId(memberIds)
                adapter.submitList(filterMembers(users, members))
                Log.d("InviteUserFragment", "Fetched users: ${users.map { it.id }}")
            }
        }

        bvm.members.observe(viewLifecycleOwner) { members ->
            val users = bvm.users.value
            if (users != null && members != null) {
                val memberIds = members.mapNotNull { it.userId }.toSet()
                adapter.updateId(memberIds)
                adapter.submitList(filterMembers(users, members))
                Log.d("InviteUserFragment", "Fetched users: ${users.map { it.id }}")
            }
        }


        adapter = InviteUserAdapter(
            addUser = { userId ->
                bvm.sendInvite(projectId, userId)
                bvm.getMember(projectId)
            },
            removeUser = { userId ->
                bvm.removeUserRequest(projectId, userId)
                bvm.getMember(projectId)
            }
        )


        checkBox.setOnCheckedChangeListener { _, isChecked ->
            showMembers = isChecked
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val input = view.findViewById<EditText>(R.id.user_edit_query)
        val sendBtn = view.findViewById<MaterialButton>(R.id.user_searchBtn)

        sendBtn.setOnClickListener {
            Log.d("Users", "No users found")
            val text = input.text.toString()
            bvm.getMember(projectId)
            if (text.isNotEmpty()) {
                bvm.searchUsers("username", text) // fixa knapp sen
            } else {
                bvm.getUsers()
            }
        }
    }


    private fun observeViewModel() {
        bvm.users.observe(viewLifecycleOwner) { users ->
            val members = bvm.members.value
            if (users != null && members != null) {
                val memberIds = members.mapNotNull { it.userId }.toSet()
                adapter.updateId(memberIds)
                adapter.submitList(filterMembers(users, members)
                )
                Log.d("InviteUserFragment", "Fetched users: ${users.map { it.id }}")
            }
        }

    }
    private fun filterMembers(
        userList: List<UserResponse>,
        memberList: List<UserProjectResponse>
    ): List<UserResponse> {
        val membersIds = memberList.mapNotNull { it.userId }.toHashSet()
        return if (showMembers) {
            userList.filter { it.id in membersIds }
        } else {
            userList.filter { it.id !in membersIds }
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



