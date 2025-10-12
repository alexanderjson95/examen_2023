package com.example.frontend_android.ui.registration

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.example.frontend_android.R
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import kotlin.math.ln

@AndroidEntryPoint
class TwoRegFragment : Fragment(R.layout.fragment_register_second) {


    private val registerViewModel: RegViewModel by hiltNavGraphViewModels(R.id.navigation_register)



        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val fName = view.findViewById<TextInputEditText>(R.id.firstNameEditText)
            val lName = view.findViewById<TextInputEditText>(R.id.lastNameEditText)
            val email = view.findViewById<TextInputEditText>(R.id.emailEditText)

            val lnameTitle = view.findViewById<TextView>(R.id.lnameTitle)
            val fnameTitle = view.findViewById<TextView>(R.id.fnameTitle)

            fName.doAfterTextChanged {
                registerViewModel.setFirstName(it.toString())
                fnameTitle.text = it.toString()
            }
            lName.doAfterTextChanged {
                registerViewModel.setLastName(it.toString())
                lnameTitle.text = it.toString()
            }
            email.doAfterTextChanged { registerViewModel.setEmail(it.toString()) }


            registerViewModel.firstName.observe(viewLifecycleOwner) { v ->
                if (fName.text.toString() != v) {
                    fName.setText(v)
                    fnameTitle.text = v
                }
            }

            registerViewModel.lastName.observe(viewLifecycleOwner) { v ->
                if (lName.text.toString() != v) {
                    lName.setText(v)
                    lnameTitle.text = v
                }
            }

            registerViewModel.email.observe(viewLifecycleOwner) { v ->
                if (email.text.toString() != v) {
                    email.setText(v)
                }
            }

        }
}