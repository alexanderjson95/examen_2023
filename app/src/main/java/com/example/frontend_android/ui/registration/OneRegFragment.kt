package com.example.frontend_android.ui.registration

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.example.frontend_android.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue



@AndroidEntryPoint
class OneRegFragment : Fragment(R.layout.fragment_register_first) {


    private val registerViewModel: RegViewModel by hiltNavGraphViewModels(R.id.navigation_register)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uname = view.findViewById<TextInputEditText>(R.id.usernameEditText)
        val pass = view.findViewById<TextInputEditText>(R.id.passwordEditText)

        pass.doAfterTextChanged { registerViewModel.setPassword(it.toString())}
        uname.doAfterTextChanged { registerViewModel.setUsername(it?.toString().orEmpty()) }


        // Håller kvar form data vid rotation etc
        registerViewModel.username.observe(viewLifecycleOwner) { v ->
            if(uname.text.toString() != v) {
                uname.setText(v)
            }
        }
        registerViewModel.password.observe(viewLifecycleOwner) {v ->
            if(pass.text.toString() != v) {
                pass.setText(v)
            }
        }

        registerViewModel.username.observe(viewLifecycleOwner) { uname ->
            Log.d("Username", "Username is: $uname")
        }



    }
}