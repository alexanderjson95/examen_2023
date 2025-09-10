package com.example.frontend_android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.frontend_android.R
import com.example.frontend_android.databinding.FragmentDashboardBinding
import com.example.frontend_android.databinding.FragmentLoginBinding
import com.example.frontend_android.ui.registration.RegisterViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login){


        private val loginViewModel: LoginViewModel by viewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.state.observe(viewLifecycleOwner) {
            if (it == true)
            {
                findNavController().navigate(R.id.navigation_booking)
            }
        }
                val loginBtn = view.findViewById<MaterialButton>(R.id.loginButton)
                val username = view.findViewById<EditText>(R.id.usernameEditText)
                val password = view.findViewById<EditText>(R.id.passwordEditText)

            loginBtn.setOnClickListener {
                loginViewModel.login(username.text.toString(), password.text.toString())
            }

        }

    }