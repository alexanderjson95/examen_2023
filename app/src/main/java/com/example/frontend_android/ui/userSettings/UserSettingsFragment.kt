package com.example.frontend_android.ui.userSettings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.frontend_android.R
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.roles.RoleRequest
import com.example.frontend_android.ui.registration.RegViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue



@AndroidEntryPoint
class UserSettingsFragment : Fragment(R.layout.fragment_user_settings) {


    private val vm: UserSettingsViewModel by viewModels()
    private lateinit var selectedRole: String
    private  var currentRole = ""



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val password = view.findViewById<EditText>(R.id.passwordEditText)
        val firstName = view.findViewById<EditText>(R.id.firstNameEditText)
        val lastName = view.findViewById<EditText>(R.id.lastNameEditText)
        val roleSpinner = view.findViewById<Spinner>(R.id.roleSpinner)
        val changeBtn = view.findViewById<MaterialButton>(R.id.changeBtn)
        val currentRoleValue = view.findViewById<TextView>(R.id.cRoleInputValue)
        val removeBtn = view.findViewById<MaterialButton>(R.id.removeBtn)

        vm.user.observe(viewLifecycleOwner){ u ->
            firstName.setText(u?.firstName)
            lastName.setText(u?.lastName)
        }


        vm.userRoles.observe(viewLifecycleOwner){ u ->
            currentRole = u.joinToString(", ")
            "Nurvarande roll: $currentRole".also { currentRoleValue.text = it }
        }



        removeBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Bekräfta borttagning")
                .setMessage("Vill du verkligen ta bort ditt konto?")
                .setPositiveButton("TA BORT") { _, _ ->
                    vm.removeUser()
                    findNavController().popBackStack(R.id.navigation_init, false)
                }
                .setNegativeButton("Avbryt", null)
                .show()
        }

        vm.updateStatus.observe(viewLifecycleOwner) { s ->
            if (s == true){
                Toast.makeText(requireContext(), "Uppdatering lyckades!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), "Uppdatering misslyckades!", Toast.LENGTH_SHORT).show()
            }
        }

        vm.roles.observe(viewLifecycleOwner) { roles ->

            val default = listOf("") + roles.map { it.roleType }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                default)

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            roleSpinner?.adapter = adapter


            roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    selectedRole = roles[position].roleType
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            changeBtn.setOnClickListener {
                val spinnerValue = roleSpinner.selectedItem.toString()
                selectedRole = spinnerValue.ifBlank { currentRole }
                val roleList = if (selectedRole.isBlank()) {
                    emptyList()
                } else {
                    listOf(RoleRequest(selectedRole))
                }


                vm.patchUser(
                    firstName = firstName.text.toString(),
                    lastName = lastName.text.toString(),
                    password = password.text.toString(),
                    roles = roleList
                )
            }
        }
    }


    fun checkInputs(list: List<EditText>, role: String): Boolean {
        for (i in list) {
            if (i.text.isNullOrBlank() && role.isEmpty()) return false
        }
        return true
    }
}