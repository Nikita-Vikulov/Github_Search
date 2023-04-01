package com.example.githubsearch.view.details

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.databinding.FragmentDetailsBinding

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    override fun getViewBinding(container: ViewGroup?): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}