package com.example.githubsearch.presentation.history

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.FragmentHistoryBinding
import com.example.githubsearch.domain.interfaces.INavigation
import com.example.githubsearch.domain.interfaces.IUserClickListener
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), IUserClickListener {

    private val historyViewModel: HistoryViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var listener: INavigation
    private val adapter by lazy { HistoryFragmentAdapter(this) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigation) {
            listener = context
        }
    }

    override fun getViewBinding(container: ViewGroup?): FragmentHistoryBinding =
        FragmentHistoryBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerView = binding.recyclerViewHistory
        recyclerView.adapter = adapter
        historyViewModel.allUsers.observe(viewLifecycleOwner) { users ->
            users.let { adapter.submitList(it) }
        }
    }

    override fun onItemClick(user: Users) {
        listener.openDetailsFragmentFromHistory(user)
    }
}
