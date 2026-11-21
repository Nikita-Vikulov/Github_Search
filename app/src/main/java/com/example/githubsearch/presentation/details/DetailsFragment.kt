package com.example.githubsearch.presentation.details

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentDetailsBinding
import com.example.githubsearch.domain.interfaces.INavigation
import com.example.githubsearch.domain.interfaces.IRepositoryClickListener
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.common.BaseFragment
import com.example.githubsearch.presentation.common.UiState
import com.example.githubsearch.util.USER
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), IRepositoryClickListener {

    private val detailsViewModel: DetailsViewModel by viewModels()

    private lateinit var currentUser: Users
    private lateinit var recyclerView: RecyclerView
    private lateinit var listener: INavigation
    private val adapter by lazy { DetailsFragmentAdapter(this) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigation) {
            listener = context
        }
    }

    override fun getViewBinding(container: ViewGroup?): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater, container, false)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser = arguments?.getParcelable<Users>(USER)
            ?: throw IllegalStateException("User argument is missing")
        init()
    }

    private fun init() {
        detailsViewModel.insert(currentUser)
        with(binding) {
            tvLogin.text = currentUser.login
            Glide.with(root.context)
                .load(currentUser.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.baseline_portrait_24)
                .into(avatarImageView)
        }
        detailsViewModel.getReposByLogin(currentUser.login)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                detailsViewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.setList(state.data)
                            if (state.data.isEmpty()) {
                                Toast.makeText(requireContext(), R.string.no_repos_found, Toast.LENGTH_SHORT).show()
                            }
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            val message = state.message.ifBlank { getString(R.string.error_loading_repos) }
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                        is UiState.Idle -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
        recyclerView = binding.recyclerViewRepos
        recyclerView.adapter = adapter
    }

    override fun onItemClick(currentRepository: Repository) {
        listener.openRepositoryFragment(currentRepository)
    }
}
