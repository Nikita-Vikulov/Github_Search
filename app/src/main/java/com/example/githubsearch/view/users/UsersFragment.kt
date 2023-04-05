package com.example.githubsearch.view.users

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.MAIN
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentUsersBinding
import com.example.githubsearch.model.Users

class UsersFragment : BaseFragment<FragmentUsersBinding>() {
    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { UsersFragmentAdapter() }

    override fun getViewBinding(container: ViewGroup?): FragmentUsersBinding =
        FragmentUsersBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        viewModel.initDatabase()
        recyclerView = binding.recyclerViewUsers
        recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getUsers(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getUsers(newText.toString())
                return false
            }

        })

        viewModel.myUsers.observe(viewLifecycleOwner) { list ->
            list.body()?.items?.let { adapter.setList(it) }
        }
    }


    companion object {
        fun clickUser(model: Users) {
            val bundle = Bundle()
            bundle.putSerializable("user", model)
            MAIN.navController.navigate(R.id.action_usersFragment_to_detailsFragment, bundle)
        }
    }
}