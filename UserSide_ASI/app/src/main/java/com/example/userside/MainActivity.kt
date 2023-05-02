package com.example.userside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.system.Os.open
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.userside.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.navView,navController)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        navController.navigate(R.id.homeFragment)
                    }
                    R.id.actualiteItem -> {
                        Toast.makeText(this@MainActivity, "Actualite Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.signalementsFragment -> {
                        navController.navigate(R.id.signalementsFragment)
                    }
                    R.id.profilItem -> {
                        Toast.makeText(this@MainActivity, "Profile Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.languesItem -> {
                        Toast.makeText(this@MainActivity, "Langues Clicked", Toast.LENGTH_SHORT).show()
                    }

                }
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}