package com.fturkan.guser.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fturkan.guser.R
import com.fturkan.guser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}