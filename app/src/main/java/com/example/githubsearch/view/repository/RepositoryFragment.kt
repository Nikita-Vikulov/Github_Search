package com.example.githubsearch.view.repository

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.REPOSITORY
import com.example.githubsearch.databinding.FragmentRepositoryBinding
import com.example.githubsearch.model.Repository

class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>() {

    private lateinit var currentRepos: Repository

    override fun getViewBinding(container: ViewGroup?): FragmentRepositoryBinding =
        FragmentRepositoryBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentRepos = arguments?.getParcelable<Repository>(REPOSITORY) ?: throw IllegalStateException("User argument is missing")
        init()
    }

    private fun init() {
        with(binding) {
            tvName.text = currentRepos.login
            tvFullName.text = currentRepos.fullName
            tvLanguage.text = currentRepos.language
            tvForksCount.text = currentRepos.forksCount.toString()
            tvDescription.text = currentRepos.description
        }
    }
}