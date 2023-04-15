package com.example.githubsearch.view.repository

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.databinding.FragmentRepositoryBinding
import com.example.githubsearch.model.Repository

class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>() {
    private lateinit var currentRepos: Repository

    override fun getViewBinding(container: ViewGroup?): FragmentRepositoryBinding =
        FragmentRepositoryBinding.inflate(layoutInflater, container, false)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentRepos = arguments?.getParcelable("repository", Repository::class.java) as Repository // parceble
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