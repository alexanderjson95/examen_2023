package com.example.frontend_android.ui.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login){
        private val loginViewModel: LoginViewModel by viewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


                val loginBtn = view.findViewById<MaterialButton>(R.id.loginButton)
                val username = view.findViewById<EditText>(R.id.usernameEditText)
                val password = view.findViewById<EditText>(R.id.passwordEditText)
                val registerMenuItem = view.findViewById<TextView>(R.id.registerMenuItem)

            loginBtn.setOnClickListener {
                loginViewModel.login(username.text.toString(), password.text.toString())
            }

            loginViewModel.state.observe(viewLifecycleOwner) {
                if (it == true)
                {
                    val action = LoginFragmentDirections.actionLoginToDashboard()
                    findNavController().navigate(action)
                }
            }
            registerMenuItem.setOnClickListener {
                val actionReg = LoginFragmentDirections.actionLoginToReg()
                findNavController().navigate(actionReg)
            }



        }

    }