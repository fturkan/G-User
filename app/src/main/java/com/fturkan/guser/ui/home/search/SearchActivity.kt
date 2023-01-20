package com.fturkan.guser.ui.home.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fturkan.guser.R
import com.fturkan.guser.adapter.SearchUserListAdapter
import com.fturkan.guser.databinding.ActivitySearchBinding
import com.fturkan.guser.data.model.User
import com.fturkan.guser.ui.home.detail.UserDetailActivity
import com.fturkan.guser.ui.visible
import com.fturkan.guser.util.KeyboardUtils


class SearchActivity : AppCompatActivity() {

    private lateinit var activityViewModel: SearchViewModel
    private lateinit var binding: ActivitySearchBinding
    private val searchRecyclerViewAdapter = SearchUserListAdapter()

    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        activityViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.apply {

            binding.viewModel = activityViewModel

            searchListRv.layoutManager = LinearLayoutManager(this@SearchActivity)
            searchListRv.setHasFixedSize(true)
            searchListRv.adapter = searchRecyclerViewAdapter

            KeyboardUtils.showKeyboard(this@SearchActivity, searchActivityEt)

            searchRecyclerViewAdapter.setOnItemClickListener(object : SearchUserListAdapter.OnItemClickListener {
                override fun onClick(data: User) {
                    startActivity(Intent(this@SearchActivity, UserDetailActivity::class.java).also {
                        it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                    })
                }
            })

            searchActivityEt.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_UP || keyEvent.action == KeyEvent.KEYCODE_ENTER || keyEvent.action == KeyEvent.KEYCODE_DEL) {
                    searchUser(searchActivityEt.text.toString())
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            searchActivitySwiperefreshLayout.setOnRefreshListener {
                searchActivitySwiperefreshLayout.isRefreshing = false
                searchUser(searchQuery)
            }

            searchActivityBackButton.setOnClickListener {
                KeyboardUtils.hideKeyboard(this@SearchActivity, searchActivityEt)
                finish()
            }

        }

        observeLiveData()

    }

    private fun observeLiveData() {

        activityViewModel.getSearchUsers().observe(this, Observer {
            if (it != null) {
                searchRecyclerViewAdapter.setUserList(it)
                binding.searchUserIv.visible = it.size <= 0
                binding.searchActivitySearchTitle.visible = it.size > 0
            }
        })

    }

    private fun searchUser(query: String) {
        binding.apply {
            activityViewModel.progressBar.value = true
            activityViewModel.fetchSearchUsers(query)
        }
    }
}