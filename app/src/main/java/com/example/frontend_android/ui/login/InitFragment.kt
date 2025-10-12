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
class InitFragment : Fragment(R.layout.fragment_init){

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

                val loginBtn = view.findViewById<MaterialButton>(R.id.loginBtn)
                val regBtn = view.findViewById<MaterialButton>(R.id.regBtn)


            loginBtn.setOnClickListener {
                val action = InitFragmentDirections.actionInitToLogin()
                view.post {
                    findNavController().navigate(action)
                }
            }
            regBtn.setOnClickListener {
                val actionReg = InitFragmentDirections.actionInitToReg()
                view.post {
                    findNavController().navigate(actionReg)
                }
            }

        }

    }