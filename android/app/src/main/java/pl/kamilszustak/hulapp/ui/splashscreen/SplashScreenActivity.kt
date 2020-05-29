package pl.kamilszustak.hulapp.ui.splashscreen

import android.os.Bundle
import org.jetbrains.anko.startActivity
import pl.kamilszustak.hulapp.ui.authentication.AuthenticationActivity
import pl.kamilszustak.hulapp.ui.base.BaseActivity

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<AuthenticationActivity>()
        finish()
    }
}