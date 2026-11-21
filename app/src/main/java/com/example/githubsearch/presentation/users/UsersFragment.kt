package com.example.githubsearch.presentation.users

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentUsersBinding
import com.example.githubsearch.domain.interfaces.INavigation
import com.example.githubsearch.domain.interfaces.IUserClickListener
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.common.BaseFragment
import com.example.githubsearch.presentation.common.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(), IUserClickListener {

    val usersViewModel: UsersViewModel by viewModels()

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersViewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.setList(state.data)
                            if (state.data.isEmpty()) {
                                Toast.makeText(requireContext(), R.string.no_users_found, Toast.LENGTH_SHORT).show()
                            }
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            val message = state.message.ifBlank { getString(R.string.error_loading_users) }
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                        is UiState.Idle -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
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
