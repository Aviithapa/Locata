package com.example.locata.ui.main

import android.hardware.Sensor
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.locata.R
import com.example.locata.ui.main.ui.dashboard.DashboardFragment
import com.example.locata.ui.main.ui.home.HomeFragment
import com.example.locata.ui.main.ui.location.MapsFragment
import com.example.locata.ui.main.ui.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout
    private lateinit var bottmNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        frameLayout = findViewById(R.id.nav_host_fragment)
        bottmNavigation = findViewById(R.id.nav_view)
        replaceFragment(HomeFragment())
        bottmNavigation.setOnNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navigation_dashboard -> {
                    replaceFragment(MapsFragment())
                    true
                }

                R.id.navigation_notifications -> {
                    replaceFragment(NotificationsFragment())
                    true
                }
                else -> false
            }
            true;
        }
    }


    fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
    }
}