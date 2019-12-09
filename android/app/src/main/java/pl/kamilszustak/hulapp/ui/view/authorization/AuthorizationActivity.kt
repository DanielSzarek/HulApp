package pl.kamilszustak.hulapp.ui.view.authorization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_authorization.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.util.navigateUp

class AuthorizationActivity : AppCompatActivity(R.layout.activity_authorization) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBarWithNavController(authorizationNavHostFragment.findNavController())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(R.id.authorizationNavHostFragment) || super.onSupportNavigateUp()
    }
}
