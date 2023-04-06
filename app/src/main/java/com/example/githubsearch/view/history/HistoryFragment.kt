package com.example.githubsearch.view.history

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.MAIN
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentHistoryBinding
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.UsersApplication
import com.example.githubsearch.view.users.UsersViewModel
import com.example.githubsearch.view.users.ViewModelFactory

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { HistoryFragmentAdapter() }
    private val usersViewModel: UsersViewModel by viewModels {
        ViewModelFactory((requireActivity().application as UsersApplication).repository)
    }

    override fun getViewBinding(container: ViewGroup?): FragmentHistoryBinding =
        FragmentHistoryBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        init()
    }

    private fun init() {
        recyclerView = binding.recyclerViewHistory
        recyclerView.adapter = adapter

        usersViewModel.allUsers.observe(viewLifecycleOwner) { users ->
            users.let { adapter.submitList(it) }
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