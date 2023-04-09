package com.example.githubsearch.view.details

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.MAIN
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentDetailsBinding
import com.example.githubsearch.model.ReposResponse
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.Application
import com.example.githubsearch.view.users.UsersViewModel
import com.example.githubsearch.view.users.ViewModelFactory

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    private lateinit var currentUser: Users //decorator для карточек
 //   private lateinit var repos: Repos //decorator для карточек
    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { DetailsFragmentAdapter() }

    private val usersViewModel: UsersViewModel by viewModels {
        ViewModelFactory((requireActivity().application as Application).userRepository)
    }

    private val detailsViewModel: DetailsViewModel by viewModels {
        ViewModelFactory((requireActivity().application as Application).repoRepository)
    }

    override fun getViewBinding(container: ViewGroup?): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        currentUser = arguments?.getSerializable("user") as Users // parceble
      //  repos = arguments?.getSerializable("repo") as Repos
        init()
    }

    private fun init() {
        usersViewModel.insert(currentUser)
      //  detailsViewModel.insert(repos)
        with(binding) {
            tvLogin.text = currentUser.login
            Glide.with(root.context)
                .load(currentUser.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(avatarImageView)
        }
        detailsViewModel.getRepos(currentUser.login)
        recyclerView = binding.recyclerViewRepos
        recyclerView.adapter = adapter
        detailsViewModel.myRepos.observe(viewLifecycleOwner) { list ->
            list.body()?.let { adapter.submitList(it) }
        }
    }

    companion object {
        fun clickRepo(model: ReposResponse) {
            val bundle = Bundle()
            bundle.putSerializable("repos", model)
            MAIN.navController.navigate(R.id.action_usersFragment_to_detailsFragment, bundle)

        }
    }
}