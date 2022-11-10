package com.example.karo

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.karo.pages.auth.LoginActivity
import com.example.karo.utils.Helpers
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.topbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }

        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().add(R.id.frame_layout, HomeFragment()).commit()

        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            item.isChecked = true
            drawer.closeDrawer(GravityCompat.START)

            when (item.itemId) {
                R.id.home -> {
                    changeFragment(HomeFragment())
                    Helpers.showToast(this, "Home is Clicked")
                }
                R.id.manage_fees -> {
                    changeFragment(ManageFeesFragment())
                    Helpers.showToast(this, "Manage Fees is Clicked")
                }
                R.id.settings -> {
                    changeFragment(SettingsFragment())
                    Helpers.showToast(this, "Settings is Clicked")
                }
                R.id.profile -> {
                    changeFragment(ProfileFragment())
                    Helpers.showToast(this, "Profile is Clicked")
                }
                R.id.share -> Helpers.showToast(this, "Share is Clicked")
                R.id.rate -> Helpers.showToast(this, "Rate is Clicked")
                R.id.logout -> logout()
                else -> return@setNavigationItemSelectedListener true
            }

            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}