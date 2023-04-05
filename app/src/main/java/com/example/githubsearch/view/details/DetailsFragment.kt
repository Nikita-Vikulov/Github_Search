package com.example.githubsearch.view.details

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentDetailsBinding
import com.example.githubsearch.model.Users

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    private lateinit var currentUser: Users //decorator для карточек
    override fun getViewBinding(container: ViewGroup?): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        currentUser = arguments?.getSerializable("user") as Users // parceble
        init()
    }

    private fun init(){
        val viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel.insert(currentUser){}
        with(binding) {
            tvLogin.text = currentUser.login
            Glide.with(root.context)
                .load(currentUser.avatar_url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(avatarImageView)
        }
    }
}