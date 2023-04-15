package com.example.githubsearch.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.ItemRepoBinding
import com.example.githubsearch.model.Repository
import com.example.githubsearch.view.IRepositoryClickListener

class DetailsFragmentAdapter(private val listener: IRepositoryClickListener) :
    ListAdapter<Repository, DetailsFragmentAdapter.DetailsViewHolder>(REPOS_COMPARATOR) {

    private var listRepos = emptyList<Repository>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DetailsViewHolder {
        val binding = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(listRepos[position])
    }

    inner class DetailsViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repos: Repository) {
            with(binding) {
                tvRepo.text = repos.name
            }
        }
    }

    override fun getItemCount(): Int {
        return listRepos.size
    }

    fun setList(list: List<Repository>) {
        listRepos = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: DetailsViewHolder) { //
        holder.itemView.setOnClickListener {
            listener.onItemClick(listRepos[holder.bindingAdapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: DetailsViewHolder) {
        holder.itemView.setOnClickListener(null)
    }

    companion object {
        private val REPOS_COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}