package com.example.githubsearch.view.users

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.App
import com.example.githubsearch.BaseFragment
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentUsersBinding
import com.example.githubsearch.model.Users
import com.example.githubsearch.view.INavigation
import com.example.githubsearch.view.IUserClickListener
import com.example.githubsearch.view.ViewModelFactory
import javax.inject.Inject

class UsersFragment :
    BaseFragment<FragmentUsersBinding>(), IUserClickListener {

    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    val usersViewModel: UsersViewModel by lazy {
        ViewModelProvider(this, viewModeFactory)[UsersViewModel::class.java]
    }

    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { UsersFragmentAdapter(this) }
    private lateinit var listener: INavigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigation) {
            listener = context
        }
    }

    override fun getViewBinding(container: ViewGroup?): FragmentUsersBinding =
        FragmentUsersBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        setupMenu()
        init()
    }

    private fun init() {
        recyclerView = binding.recyclerViewUsers
        recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                usersViewModel.getUsersByLogin(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        usersViewModel.myUsers.observe(viewLifecycleOwner) { list ->
            adapter.setList(list)
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.item_history -> {
                        listener.openHistoryFragment()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onItemClick(user: Users) {
        listener.openDetailsFragmentFromUsers(user)
    }
}