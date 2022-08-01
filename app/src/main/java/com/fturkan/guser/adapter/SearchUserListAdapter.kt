package com.fturkan.guser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fturkan.guser.R
import com.fturkan.guser.data.model.User
import com.fturkan.guser.databinding.UserListRowBinding

class SearchUserListAdapter: RecyclerView.Adapter<SearchUserListAdapter.SearchUserListViewHolder>() {

    private val list = ArrayList<User>()
    private lateinit var onItemClickListener: OnItemClickListener

    fun setUserList(users: ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    inner class SearchUserListViewHolder(val binding: UserListRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.apply {

                binding.root.setOnClickListener {
                    onItemClickListener.onClick(user)
                }

                // Set list item user avatar
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .into(userAvatarIv)

                // Set list item user name
                userName.text = user.login

                var check = false

                userFavoriteButton.setOnClickListener {
                    if (check) {
                        Glide.with(itemView).load(R.drawable.ic_baseline_favorite_border_24).into(userFavoriteButton)
                        check = false
                    } else {
                        Glide.with(itemView).load(R.drawable.ic_baseline_favorite_24).into(userFavoriteButton)
                        check = true
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserListViewHolder {
        val view = UserListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchUserListViewHolder((view))
    }

    override fun onBindViewHolder(holder: SearchUserListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onClick(data: User)
    }
    
}