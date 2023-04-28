package com.example.githubsearch.view.history

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.App
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.databinding.FragmentHistoryBinding
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.INavigation
import com.example.githubsearch.view.IUserClickListener
import com.example.githubsearch.view.ViewModelFactory
import javax.inject.Inject

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), IUserClickListener {

    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private val historyViewModel: HistoryViewModel by lazy {
        ViewModelProvider(this, viewModeFactory)[HistoryViewModel::class.java]
    }

    private lateinit var recyclerView: RecyclerView
    lateinit var listener: INavigation
    private val adapter by lazy { HistoryFragmentAdapter(this) }

    override fun getViewBinding(container: ViewGroup?): FragmentHistoryBinding =
        FragmentHistoryBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        listener = (requireActivity() as? INavigation)!!
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