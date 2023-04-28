package com.example.githubsearch.view.details

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.*
import com.example.githubsearch.databinding.FragmentDetailsBinding
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.INavigation
import com.example.githubsearch.view.IRepositoryClickListener
import com.example.githubsearch.view.ViewModelFactory
import javax.inject.Inject


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), IRepositoryClickListener {

    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private val detailsViewModel: DetailsViewModel by lazy {
        ViewModelProvider(this, viewModeFactory)[DetailsViewModel::class.java]
    }

    private lateinit var currentUser: Users
    private lateinit var recyclerView: RecyclerView
    private lateinit var listener: INavigation
    private val adapter by lazy { DetailsFragmentAdapter(this) }

    override fun getViewBinding(container: ViewGroup?): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater, container, false)


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        currentUser = arguments?.getParcelable<Users>(USER)
            ?: throw IllegalStateException("User argument is missing")
        init()
        listener = (requireActivity() as? INavigation)!!
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