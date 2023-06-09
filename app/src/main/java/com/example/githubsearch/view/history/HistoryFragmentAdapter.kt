package com.example.githubsearch.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ItemUserBinding
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.IUserClickListener


class HistoryFragmentAdapter(private val listener: IUserClickListener) : RecyclerView.Adapter<HistoryFragmentAdapter.HistoryViewHolder>() {

    private var listUsers = emptyList<Users>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    inner class HistoryViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(users: Users) {
            with(binding) {
                tvLogin.text = users.login
                Glide.with(root.context)
                    .load(users.avatarUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(avatarImageView)
            }
        }
    }
    fun submitList(list: List<Users>) {
        listUsers = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onViewAttachedToWindow(holder: HistoryViewHolder) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(listUsers[holder.bindingAdapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: HistoryViewHolder) {
        holder.itemView.setOnClickListener(null)
    }
}