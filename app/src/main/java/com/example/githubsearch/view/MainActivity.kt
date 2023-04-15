package com.example.githubsearch.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users

class MainActivity : AppCompatActivity(), INavigation {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.item_history -> {
                openHistoryFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun openHistoryFragment() {
        navController.navigate(R.id.action_usersFragment_to_historyFragment)
    }

    override fun openRepositoryFragment(currentRepository: Repository) {
        val bundle = Bundle()
        bundle.putParcelable("repository", currentRepository)
        navController.navigate(R.id.action_detailsFragment_to_repositoryFragment, bundle)
    }

    override fun openDetailsFragmentFromHistory(users: Users) {
        val bundle = Bundle()
        bundle.putParcelable("user", users)
        navController.navigate(R.id.action_historyFragment_to_detailsFragment, bundle)
    }

    override fun openDetailsFragmentFromUsers(users: Users) {
        val bundle = Bundle()
        bundle.putParcelable("user", users)
        navController.navigate(R.id.action_usersFragment_to_detailsFragment, bundle)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

//flavors сборки

