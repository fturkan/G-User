package com.fturkan.guser.ui.home.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fturkan.guser.adapter.FavoriteUsersListAdapter
import com.fturkan.guser.data.local.UserDatabase
import com.fturkan.guser.data.model.User
import com.fturkan.guser.databinding.FragmentFavoritesBinding
import com.fturkan.guser.ui.home.detail.UserDetailActivity

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private val favoritesRecyclerViewAdapter = FavoriteUsersListAdapter()

    private var database: UserDatabase? = activity?.let { UserDatabase.getDatabase(it.application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@FavoritesFragment)[FavoritesViewModel::class.java]

        fetchFavoriteList()

        binding.apply {

            favoritesFragmentRvUserList.layoutManager = LinearLayoutManager(context)
            favoritesFragmentRvUserList.setHasFixedSize(true)
            favoritesFragmentRvUserList.adapter = favoritesRecyclerViewAdapter

            favoritesRecyclerViewAdapter.setOnItemClickListener(object : FavoriteUsersListAdapter.OnItemClickListener {
                override fun onClick(data: User) {
                    startActivity(Intent(requireActivity(), UserDetailActivity::class.java).also {
                        it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        it.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                        it.putExtra(UserDetailActivity.EXTRA_UUID, data.id)
                    })
                }
            })

            favoritesFragmentSwiperefreshLayout.setOnRefreshListener {
                progressBar.visibility = View.VISIBLE
                favoritesFragmentRvUserList.visibility = View.GONE
                favoritesFragmentSwiperefreshLayout.isRefreshing = false
                fetchFavoriteList()
            }

        }

        observeLiveData()

    }

    override fun onResume() {
        super.onResume()
        fetchFavoriteList()
    }

    private fun fetchFavoriteList() {
        binding.apply {
            showLoading(true)
            viewModel.fetchFavoriteUsers()
        }
    }

    private fun observeLiveData(){

        viewModel.getFavoriteUsersList().observe(viewLifecycleOwner) {
            if (it != null) {
                favoritesRecyclerViewAdapter.setUserList(it)
                binding.favoritesFragmentRvUserList.visibility = View.VISIBLE

                if (it.size == 0) binding.emptyFavoritesLayout.visibility = View.VISIBLE
                else binding.emptyFavoritesLayout.visibility = View.GONE

                showLoading(false)
            }
        }

    }

    private fun showLoading(state: Boolean){
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

}