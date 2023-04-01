package com.example.githubsearch.view.users

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
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
        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (context?.let { isOnline(it) } == true) {
                    viewModel.getUsers(query.toString())
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                /* if (newText != null) {
                     viewModel.getUsers(newText.toString())
                 }*/
                return false
            }

        })
        if (context?.let { isOnline(it) } == true) {
            viewModel.myUsers.observe(viewLifecycleOwner) { list ->
                list.body()!!.items?.let { adapter.setList(it) }
            }
        } else {
            viewModel.getAllUsers().observe(viewLifecycleOwner) { list ->
                adapter.setList(list.asReversed() as ArrayList<Users>)
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    companion object {
        fun clickUser(model: Users) {
            val bundle = Bundle()
            bundle.putSerializable("user", model)
            MAIN.navController.navigate(R.id.action_usersFragment_to_detailsFragment, bundle)

        }
    }
}