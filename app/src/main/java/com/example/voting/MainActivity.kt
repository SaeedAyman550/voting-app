package com.example.voting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.voting.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupBottomNavigationWithNavController()
        hideBottomNavBars()




    }

    private fun setupBottomNavigationWithNavController() {
        val controller = getSupportFragmentManager()
            .findFragmentById(R.id.main_activity_nav_host_fragment_containerfragment)
            ?.findNavController()

        if (controller != null) {
            binding.mainActivityBottomNavigationView.setupWithNavController(controller)
        }
    }

    private fun hideBottomNavBars() {
        val navController = getSupportFragmentManager()
            .findFragmentById(R.id.main_activity_nav_host_fragment_containerfragment)
            ?.findNavController()
        navController?.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {
                R.id.candidates -> {
                    binding.mainActivityBottomNavigationView.visibility = View.VISIBLE
                    binding.view.visibility=View.VISIBLE
                }
                else -> {
                    binding.mainActivityBottomNavigationView.visibility = View.GONE
                    binding.view.visibility=View.GONE

                }
            }
        }

    }

}