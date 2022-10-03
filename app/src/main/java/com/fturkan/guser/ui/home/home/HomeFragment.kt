package com.fturkan.guser.ui.home.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fturkan.guser.data.model.User
import com.fturkan.guser.databinding.FragmentHomeBinding
import com.fturkan.guser.ui.home.detail.UserDetailActivity
import com.fturkan.guser.adapter.UserListAdapter

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val homeRecyclerViewAdapter = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]

        searchRandomUser()

        binding.apply {

            homeFragmentRv.layoutManager = LinearLayoutManager(context)
            homeFragmentRv.setHasFixedSize(true)
            homeFragmentRv.adapter = homeRecyclerViewAdapter

            headerSearchLl.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchActivity())
            }

            homeRecyclerViewAdapter.setOnItemClickListener(object : UserListAdapter.OnItemClickListener {
                override fun onClick(data: User) {
                    startActivity(Intent(requireActivity(), UserDetailActivity::class.java).also {
                        it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        it.putExtra(UserDetailActivity.EXTRA_UUID, data.id)
                    })
                }
            })

            homeFragmentSwiperefreshLayout.setOnRefreshListener {
                homeProgressBar.visibility = View.VISIBLE
                homeFragmentRv.visibility = View.GONE
                homeFragmentSwiperefreshLayout.isRefreshing = false
                searchRandomUser()
            }

        }

        observeLiveData()
    }

    private fun observeLiveData(){

        viewModel.getSearchUsers().observe(viewLifecycleOwner) {
            if (it != null) {
                homeRecyclerViewAdapter.setUserList(it)
                showLoading(false)
                binding.homeFragmentRv.visibility = View.VISIBLE
            }
        }

    }

    private fun searchRandomUser(){
        binding.apply {
            showLoading(true)
            viewModel.fetchSearchUsers(randomID())
        }
    }

    private fun randomID(): String = List(1) {
        (('a'..'z') + ('A'..'Z')).random()
    }.joinToString("")

    private fun showLoading(state: Boolean){
        if (state) binding.homeProgressBar.visibility = View.VISIBLE
        else binding.homeProgressBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}