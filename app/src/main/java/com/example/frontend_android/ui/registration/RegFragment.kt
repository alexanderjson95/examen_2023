package com.example.frontend_android.ui.registration

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.frontend_android.R
import com.example.frontend_android.model.roles.RoleRequest
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator3

@AndroidEntryPoint
class RegFragment : Fragment(R.layout.fragment_register) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vp = view.findViewById<ViewPager2>(R.id.viewPager)
        vp.adapter = RegPagerAdapter(this)
        vp.isUserInputEnabled = true
        val indicator = view.findViewById<CircleIndicator3>(R.id.indicator)
        val loginMenuItem = view.findViewById<TextView>(R.id.loginMenuItem)
        val next = view.findViewById<MaterialButton>(R.id.nextBtn)
        val back = view.findViewById<MaterialButton>(R.id.backBtn)
        indicator.setViewPager(vp)

        loginMenuItem.setOnClickListener {
            val action = RegFragmentDirections.actionRegToLogin()
            findNavController().navigate(action)
        }

        next.setOnClickListener {
            val next = vp.currentItem + 1
            vp.setCurrentItem(next,true)
        }

        back.setOnClickListener {
            val past = vp.currentItem - 1
            vp.setCurrentItem(past,true)
        }

        vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                next.visibility = if (position == 2) View.GONE else View.VISIBLE
                back.visibility = if (position == 0) View.GONE else View.VISIBLE
            }
        })
    }
}
