package com.example.githubsearch.view.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.BASE_URL
import com.example.githubsearch.MAIN
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ItemUserBinding
import com.example.githubsearch.model.Users

class UsersFragmentAdapter : RecyclerView.Adapter<UsersFragmentAdapter.UsersViewHolder>() {

    private var listUsers = emptyList<Users>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UsersViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }


    override fun getItemCount(): Int {
        return listUsers.size
    }

    inner class UsersViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(users: Users) {
            with(binding) {
                tvLogin.text = users.login
                /*Glide.with(MAIN)
                    .load("${BASE_URL}${listUsers[position].avatar_url}")
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(avatarImageView)*/
            }
        }
    }

    fun setList(list: List<Users>) {
        listUsers = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: UsersViewHolder) {
        holder.itemView.setOnClickListener {
            UsersFragment.clickMovie(listUsers[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: UsersViewHolder) {
        holder.itemView.setOnClickListener(null)
    }
}