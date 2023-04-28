package com.example.githubsearch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.githubsearch.R
import com.example.githubsearch.REPOSITORY
import com.example.githubsearch.USER
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users
import javax.inject.Inject

class MainActivity : AppCompatActivity(), INavigation {
    @Inject
    lateinit var navigation: INavigation

    private lateinit var userClickListener: IUserClickListener
    private lateinit var repositoryClickListener: IRepositoryClickListener

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host)
    }

    override fun openHistoryFragment() {
        navController.navigate(R.id.action_usersFragment_to_historyFragment)
    }

    override fun openRepositoryFragment(currentRepository: Repository) {
        navController.navigate(
            R.id.action_detailsFragment_to_repositoryFragment,
            bundleOf(REPOSITORY to currentRepository)
        )
    }

    override fun openDetailsFragmentFromHistory(users: Users) {
        navController.navigate(
            R.id.action_historyFragment_to_detailsFragment,
            bundleOf(USER to users)
        )
    }

    override fun openDetailsFragmentFromUsers(users: Users) {
        navController.navigate(
            R.id.action_usersFragment_to_detailsFragment,
            bundleOf(USER to users)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

