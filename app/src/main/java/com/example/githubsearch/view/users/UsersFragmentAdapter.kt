package com.example.githubsearch.view.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ItemUserBinding
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.IUserClickListener

class UsersFragmentAdapter(private val listener: IUserClickListener) :
    ListAdapter<Users, UsersFragmentAdapter.UsersViewHolder>(USERS_COMPARATOR) {

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

    inner class UsersViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(users: Users) {
            with(binding) {
                tvLogin.text = users.login
                Glide.with(root.context)
                    .load(users.avatarUrl)
                    .centerCrop()
                    .placeholder(R.drawable.baseline_portrait_24)
                    .into(avatarImageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    fun setList(list: List<Users>) {
        listUsers = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: UsersViewHolder) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(listUsers[holder.bindingAdapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: UsersViewHolder) {
        holder.itemView.setOnClickListener(null)
    }

    companion object {
        private val USERS_COMPARATOR = object : DiffUtil.ItemCallback<Users>() {
            override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

