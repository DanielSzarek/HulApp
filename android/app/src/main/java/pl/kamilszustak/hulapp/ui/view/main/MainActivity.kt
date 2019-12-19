package pl.kamilszustak.hulapp.ui.view.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.ui.view.base.BaseActivity
import pl.kamilszustak.hulapp.util.setupWithNavController

class MainActivity : BaseActivity(R.layout.activity_main) {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

//        initializeNavigation()
//        setListeners()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null)
            super.onRestoreInstanceState(savedInstanceState)

        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.navigation_profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = mainBottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.mainNavHostFragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this) {
            setupActionBarWithNavController(it)
        }

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navigateUp(R.id.mainNavHostFragment) || super.onSupportNavigateUp()
//    }

//    private fun initializeNavigation() {
//        val navController = mainNavHostFragment.findNavController()
//        mainBottomNavigationView.setupWithNavController(navController)
//        setupActionBarWithNavController(navController)
//    }
//
//    private fun setListeners() {
//        mainBottomNavigationView.setOnNavigationItemSelectedListener {
//            val selectedItemId = mainBottomNavigationView.selectedItemId
//            it.itemId != selectedItemId
//        }
//    }
}