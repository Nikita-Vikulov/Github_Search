package com.example.githubsearch.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.ItemRepoBinding
import com.example.githubsearch.model.ReposResponse

class DetailsFragmentAdapter : ListAdapter<ReposResponse, DetailsFragmentAdapter.DetailsViewHolder>(REPOS_COMPARATOR) {

    private var listRepos = ArrayList<ReposResponse>()

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

        fun bind(repos: ReposResponse) {
            with(binding) {
                tvRepo.text = repos.name
            }
        }
    }

    override fun getItemCount(): Int {
        return listRepos.size
    }

    fun submitList(list: MutableList<ReposResponse>) {
        listRepos =  ArrayList(list)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: DetailsViewHolder) {
        holder.itemView.setOnClickListener {
            DetailsFragment.clickRepo(listRepos[holder.bindingAdapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: DetailsViewHolder) {
        holder.itemView.setOnClickListener(null)
    }


    companion object {
        private val REPOS_COMPARATOR = object : DiffUtil.ItemCallback<ReposResponse>() {
            override fun areItemsTheSame(oldItem: ReposResponse, newItem: ReposResponse): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ReposResponse, newItem: ReposResponse): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}