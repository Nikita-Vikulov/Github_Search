package com.example.githubsearch.view.repository

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.databinding.FragmentRepositoryBinding
import com.example.githubsearch.model.ReposResponse
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.Application
import com.example.githubsearch.view.details.DetailsViewModel
import com.example.githubsearch.view.users.UsersViewModel
import com.example.githubsearch.view.users.ViewModelFactory

class RepositoryFragment: BaseFragment<FragmentRepositoryBinding>() {
    private lateinit var currentUser: Users //decorator для карточек
    private lateinit var currentRepos: ReposResponse
    private lateinit var recyclerView: RecyclerView

    private val usersViewModel: UsersViewModel by viewModels {
        ViewModelFactory((requireActivity().application as Application).userRepository)
    }

    private val detailsViewModel: DetailsViewModel by viewModels {
        com.example.githubsearch.view.details.ViewModelFactory((requireActivity().application as Application).repoRepository)
    }

    override fun getViewBinding(container: ViewGroup?): FragmentRepositoryBinding =
        FragmentRepositoryBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        currentRepos = arguments?.getSerializable("repos") as ReposResponse // parceble
        init()

    }

    private fun init(){
        /*usersViewModel.insert(currentUser)
        recyclerView = binding.recyclerViewRepos
        recyclerView.adapter = adapter
        detailsViewModel.getReposByLogin(currentUser.login)
        detailsViewModel.myRepos.observe(viewLifecycleOwner) { list ->
            list.body()?.let { adapter.submitList(it) }
        }*/

    }
}