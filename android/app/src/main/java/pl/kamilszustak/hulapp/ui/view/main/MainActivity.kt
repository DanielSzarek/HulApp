package pl.kamilszustak.hulapp.ui.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.util.navigateUp

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeNavigation()
        setListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(R.id.mainNavHostFragment) || super.onSupportNavigateUp()
    }

    private fun initializeNavigation() {
        val navController = mainNavHostFragment.findNavController()
        mainBottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
    }

    private fun setListeners() {
        mainBottomNavigationView.setOnNavigationItemSelectedListener {
            val selectedItemId = mainBottomNavigationView.selectedItemId
            it.itemId != selectedItemId
        }
    }
}