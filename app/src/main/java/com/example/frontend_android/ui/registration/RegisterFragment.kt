package com.example.frontend_android.ui.registration

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.frontend_android.R
import com.example.frontend_android.model.roles.RoleRequest
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register){


        private val registerViewModel: RegisterViewModel by viewModels()

        private var selectedRole: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val regBtn = view.findViewById<MaterialButton>(R.id.regBtn)
            val username = view.findViewById<EditText>(R.id.usernameEditText)
            val password = view.findViewById<EditText>(R.id.passwordEditText)
            val firstName = view.findViewById<EditText>(R.id.firstNameEditText)
            val lastName = view.findViewById<EditText>(R.id.lastNameEditText)
            val email = view.findViewById<EditText>(R.id.emailEditText)
            val loginMenuItem = view.findViewById<TextView>(R.id.loginMenuItem)
            val roleSpinner = view.findViewById<Spinner>(R.id.roleSpinner)
            registerViewModel.getRoles()

            registerViewModel.status.observe(viewLifecycleOwner) { status ->
                if (status){
                    val action = RegisterFragmentDirections.actionRegToLogin()
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), "Registrering lyckades!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Registrering misslyckades!", Toast.LENGTH_SHORT).show()
                }
            }

            registerViewModel.roles.observe(viewLifecycleOwner) { roles ->
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    roles.map { it.roleType ?: "Fel i hämtning" }                )

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
                loginMenuItem.setOnClickListener {
                    val action = RegisterFragmentDirections.actionRegToLogin()
                    findNavController().navigate(action)
                }
                regBtn.setOnClickListener {
                    val selectedRole = roleSpinner.selectedItem.toString()
                    val roleList = listOfNotNull(
                        RoleRequest(selectedRole)
                    )

                    registerViewModel.register(
                        username = username.text.toString(),
                        firstName = firstName.text.toString(),
                        lastName = lastName.text.toString(),
                        password = password.text.toString(),
                        email = email.text.toString(),
                        publicKey = null,
                        roles = roleList
                    )
                }
            }

        }

    }