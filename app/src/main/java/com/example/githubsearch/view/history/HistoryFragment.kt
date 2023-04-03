package com.example.githubsearch.view.history

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.MAIN
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentHistoryBinding
import com.example.githubsearch.model.Users

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { HistoryFragmentAdapter() }

    override fun getViewBinding(container: ViewGroup?): FragmentHistoryBinding =
        FragmentHistoryBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        recyclerView = binding.recyclerViewHistory
        recyclerView.adapter = adapter

        viewModel.getAllUsers().observe(viewLifecycleOwner) { list ->
            adapter.setList(list.asReversed())
        }
    }

    companion object {
        fun clickUser(model: Users) {
            val bundle = Bundle()
            bundle.putSerializable("user", model)
            MAIN.navController.navigate(R.id.action_historyFragment_to_detailsFragment, bundle)
        }
    }
}