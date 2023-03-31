package com.example.githubsearch.view.users

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
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
        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        viewModel.getUsers()
        viewModel.myUsers.observe(viewLifecycleOwner) { list ->
            list.body()!!.items?.let { adapter.setList(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        fun clickMovie(model: Users) {
            val bundle = Bundle()
            bundle.putSerializable("user", model)
            MAIN.navController.navigate(R.id.action_usersFragment_to_detailsFragment, bundle)

        }
    }
}