package com.fturkan.guser.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.fturkan.guser.FavoritesFragment
import com.fturkan.guser.HomeFragment
import com.fturkan.guser.ProfileFragment
import com.fturkan.guser.R
import com.fturkan.guser.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottom_home_item -> replaceFragment(HomeFragment())
                R.id.bottom_favorite_item -> replaceFragment(FavoritesFragment())
                R.id.bottom_profile_item -> replaceFragment(ProfileFragment())
            }
            true
        }

    }

    private fun replaceFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homeActivity_fl, fragment)
        transaction.commit()
    }
}