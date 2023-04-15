package com.example.githubsearch.view.details

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentDetailsBinding
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.Application
import com.example.githubsearch.view.INavigation
import com.example.githubsearch.view.IRepositoryClickListener


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), IRepositoryClickListener {
    private lateinit var currentUser: Users
    private lateinit var recyclerView: RecyclerView
    lateinit var listener: INavigation
    private val adapter by lazy { DetailsFragmentAdapter(this) }

    private val detailsViewModel: DetailsViewModel by viewModels {
        ViewModelFactory(
            (requireActivity().application as Application).userRepository,
            (requireActivity().application as Application).repoRepository
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is INavigation) {
            listener = context
        }
    }
    override fun getViewBinding(container: ViewGroup?): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater, container, false)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser = arguments?.getParcelable("user", Users::class.java)!!
        init()
    }

    private fun init() {
        detailsViewModel.insert(currentUser)
        with(binding) {
            tvLogin.text = currentUser.login
            Glide.with(root.context)
                .load(currentUser.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(avatarImageView)
        }
        detailsViewModel.getRepos(currentUser.login)
        detailsViewModel.myRepos.observe(viewLifecycleOwner) { data ->
                adapter.setList(data)
        }
        recyclerView = binding.recyclerViewRepos
        recyclerView.adapter = adapter
    }

    override fun onItemClick(currentRepository: Repository) {
       listener.openRepositoryFragment(currentRepository)
    }
}