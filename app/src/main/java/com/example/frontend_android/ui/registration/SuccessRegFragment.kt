package com.example.frontend_android.ui.registration

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.frontend_android.R
import com.example.frontend_android.model.roles.RoleRequest
import com.example.frontend_android.model.roles.RoleType
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import okhttp3.internal.immutableListOf
import java.util.concurrent.TimeUnit
import kotlin.getValue

@AndroidEntryPoint
class SuccessRegFragment : Fragment(R.layout.fragment_register_success) {


    private lateinit var konfetti: KonfettiView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginNav = view.findViewById<MaterialButton>(R.id.navLoginBtn)

        konfetti = view.findViewById<KonfettiView>(R.id.konfettiView)
        konfetti.start(
            Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(Color.YELLOW, Color.GREEN, Color.MAGENTA),
                emitter = Emitter(duration = 10, TimeUnit.SECONDS).perSecond(30)
            )
        )


            loginNav.setOnClickListener {
                val action = SuccessRegFragmentDirections.actionRegToLogin()
                findNavController().navigate(action)
            }
        }

}