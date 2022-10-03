package com.fturkan.guser.ui.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fturkan.guser.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@ProfileFragment)[ProfileViewModel::class.java]

        viewModel.setUserDetail("fturkan")

        observeLiveData()

    }

    private fun observeLiveData(){

        viewModel.getUserDetail().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@ProfileFragment).load(it.avatar_url).into(userDetailAvatar)
                    userDetailNameSurname.text = it.name
                    userDetailUsername.text = it.login
                    userDetailFollowInfo.text = "${it.followers} Followers | ${it.following} Following"

                    userDetailCardView.visibility = View.VISIBLE
                    userDetailProgressBar.visibility = View.GONE
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}