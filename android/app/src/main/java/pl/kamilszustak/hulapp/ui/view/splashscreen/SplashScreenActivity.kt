package pl.kamilszustak.hulapp.ui.view.splashscreen

import android.os.Bundle
import org.jetbrains.anko.startActivity
import pl.kamilszustak.hulapp.ui.view.authorization.AuthorizationActivity
import pl.kamilszustak.hulapp.ui.view.base.BaseActivity

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<AuthorizationActivity>()
        finish()
    }
}