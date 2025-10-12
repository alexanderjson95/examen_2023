package com.example.frontend_android

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.databinding.ActivityMainBinding
import com.example.frontend_android.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_myProject, R.id.navigation_dashboard, R.id.navigation_projects,R.id.nav_logout
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.navigation_success_register,
                R.id.navigation_init,
                R.id.navigation_register,
                R.id.navigation_login -> {
                    navView.visibility = View.GONE
                    supportActionBar?.hide()
                }

                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }

        navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_logout -> {
                    sessionManager.clear()
                    navController.popBackStack(R.id.navigation_init, false)
                    true
                } else-> {
                    navController.navigate(item.itemId)
                    true
                }
            }
        }
    }
}