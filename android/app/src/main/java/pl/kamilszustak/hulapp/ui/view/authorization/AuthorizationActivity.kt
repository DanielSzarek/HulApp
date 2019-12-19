package pl.kamilszustak.hulapp.ui.view.authorization

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_authorization.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.ui.view.base.BaseActivity
import pl.kamilszustak.hulapp.util.navigateUp

class AuthorizationActivity : BaseActivity(R.layout.activity_authorization) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBarWithNavController(authorizationNavHostFragment.findNavController())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(R.id.authorizationNavHostFragment) || super.onSupportNavigateUp()
    }
}
