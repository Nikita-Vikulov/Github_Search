package com.example.githubsearch.view.users

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.databinding.FragmentUsersBinding
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.Application
import com.example.githubsearch.view.INavigation
import com.example.githubsearch.view.IUserClickListener

class UsersFragment : BaseFragment<FragmentUsersBinding>(), IUserClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var listener: INavigation
    private val adapter by lazy { UsersFragmentAdapter(this) }


    private val usersViewModel: UsersViewModel by viewModels {
        ViewModelFactory((requireActivity().application as Application).userRepository)
    }


    override fun getViewBinding(container: ViewGroup?): FragmentUsersBinding =
        FragmentUsersBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerView = binding.recyclerViewUsers
        recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                usersViewModel.getUsersByLogin(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                usersViewModel.getUsersByLogin(newText.toString())
                return false
            }
        })
        usersViewModel.myUsers.observe(viewLifecycleOwner) { list ->
            adapter.setList(list)
        }
    }

    override fun onAttach(context: Context) { //найти способ без onAttach
        super.onAttach(context)
        if (context is INavigation) {
            listener = context
        }
    }

    override fun onItemClick(user: Users) {
        listener.openDetailsFragmentFromUsers(user)
    }
}