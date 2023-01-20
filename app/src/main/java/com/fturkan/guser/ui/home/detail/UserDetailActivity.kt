package com.fturkan.guser.ui.home.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fturkan.guser.databinding.ActivityUserDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserDetailBinding
    private lateinit var activityViewModel: UserDetailViewModel
    private lateinit var username: String
    private var uuid: Int = 0
    private var _isFavoriteChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)

        username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        uuid = intent.getIntExtra(EXTRA_UUID, 0)

        activityViewModel.setUserDetail(username)

        observeLiveData()

        with(binding){

            CoroutineScope(Dispatchers.IO).launch {
                val count = activityViewModel.checkUser(uuid)
                withContext(Dispatchers.Main) {
                    if (count != null){
                        if (count > 0) {
                            userDetailFavoriteToggle.isChecked = true
                            _isFavoriteChecked = true
                        } else {
                            userDetailFavoriteToggle.isChecked = false
                            _isFavoriteChecked = false
                        }
                    }
                }
            }

            userDetailFavoriteToggle.setOnClickListener {
                _isFavoriteChecked = !_isFavoriteChecked
                if (_isFavoriteChecked){
                    activityViewModel.addToFavorite(username, uuid)
                } else {
                    activityViewModel.removeFromFavorite(uuid)
                }
                userDetailFavoriteToggle.isChecked = _isFavoriteChecked
            }

            headerHomeLayout.setOnClickListener {
                onBackPressed()
            }

        }

    }

    private fun observeLiveData(){
        activityViewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@UserDetailActivity).load(it.avatar_url).into(userDetailAvatar)
                    userDetailNameSurname.text = it.name
                    userDetailUsername.text = it.login
                    userDetailTvFollowers.text = it.followers
                    userDetailTvFollowing.text = it.following

                    userDetailCardView.visibility = View.VISIBLE
                    userDetailProgressBar.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_UUID = "extra_uuid"
    }
}