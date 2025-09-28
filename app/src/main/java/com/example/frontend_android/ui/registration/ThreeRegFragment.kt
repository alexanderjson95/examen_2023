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
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
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
class ThreeRegFragment : Fragment(R.layout.fragment_register_third) {


    private val registerViewModel: RegViewModel by hiltNavGraphViewModels(R.id.navigation_register)
    private var selectedRole: String? = RoleType.Övrigt.toString()
    private lateinit var alert_card: CardView
    private lateinit var alert_value: TextView
    private lateinit var konfetti: KonfettiView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roleSpinner = view.findViewById<Spinner>(R.id.roleSpinner)
        val roleTitle = view.findViewById<TextView>(R.id.roleTitle)
        val regBtn = view.findViewById<MaterialButton>(R.id.regBtn)
        alert_card = view.findViewById<CardView>(R.id.alert_card)
        alert_value = view.findViewById<TextView>(R.id.alert_value)
        konfetti = view.findViewById<KonfettiView>(R.id.konfettiView)
        val invalidInputMsg = "Alla fält måste fyllas i!"
        val successMsg = "Registreringen lyckades!"
        val errorMsg = "Något gick fel! Kunde inte registrera!"


        registerViewModel.getRoles()

        registerViewModel.status.observe(viewLifecycleOwner) { status ->
            if (status) {
                val action = RegFragmentDirections.actionRegToSuccess()
                findNavController().navigate(action)
            } else {
                alert(errorMsg)
            }
        }
        registerViewModel.roles.observe(viewLifecycleOwner) { roles ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                roles.map { it.roleType })

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            roleSpinner?.adapter = adapter
            roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    selectedRole = roles[position].roleType
                    roleTitle.text = roles[position].roleType
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }



            regBtn.setOnClickListener {
                try{
                    val username = requireNotNull(registerViewModel.username.value) {
                        alert(invalidInputMsg)
                    }
                    val firstName =
                        requireNotNull(registerViewModel.firstName.value) { alert(invalidInputMsg) }
                    val lastName =
                        requireNotNull(registerViewModel.lastName.value) { alert(invalidInputMsg) }
                    val password =
                        requireNotNull(registerViewModel.password.value) { alert(invalidInputMsg) }
                    val email = requireNotNull(registerViewModel.email.value) { alert(invalidInputMsg) }

                    val selectedRole = roleSpinner.selectedItem.toString()

                    val roleList = listOfNotNull(
                        RoleRequest(selectedRole)
                    )

                    registerViewModel.register(
                        username = username,
                        firstName = firstName,
                        lastName = lastName,
                        password = password,
                        email = email,
                        publicKey = null,
                        roles = roleList
                    )
                }catch (e: IllegalArgumentException) {
                    Toast.makeText(requireContext(), e.message ?: "Registrering misslyckades", Toast.LENGTH_SHORT).show()
                    alert(errorMsg)
                }
            }
        }

    }


    fun alert(msg:String) {
        alert_card.visibility = View.VISIBLE
        alert_value.text = msg
    }

    fun confetti(){
        konfetti.start(
            Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(Color.YELLOW, Color.GREEN, Color.MAGENTA),
                emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(30)
            )
        )

    }
}