package com.example.githubsearch.view.history

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.MainViewModel
import com.example.githubsearch.ViewModelFactory
import com.example.githubsearch.databinding.FragmentHistoryBinding
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.Application
import com.example.githubsearch.view.INavigation
import com.example.githubsearch.view.IUserClickListener

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), IUserClickListener {

    private lateinit var recyclerView: RecyclerView
    lateinit var listener: INavigation
    private val adapter by lazy { HistoryFragmentAdapter(this) }

    private val mainViewModel: MainViewModel by viewModels {
       ViewModelFactory(
            (requireActivity().application as Application).userRepository,
            (requireActivity().application as Application).repoRepository
        )
    }

    override fun getViewBinding(container: ViewGroup?): FragmentHistoryBinding =
        FragmentHistoryBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (requireActivity() as? INavigation)!!
        init()
    }

    private fun init() {
        recyclerView = binding.recyclerViewHistory
        recyclerView.adapter = adapter

        mainViewModel.allUsers.observe(viewLifecycleOwner) { users ->
            users.let { adapter.submitList(it) }
        }
    }

   /* override fun onAttach(context: Context) { //найти способ без onAttach
        super.onAttach(context)
        if (context is INavigation) {
            listener = context
        }
    }*/

    override fun onItemClick(user: Users) {
        listener.openDetailsFragmentFromHistory(user)
    }
}