package pl.kamilszustak.hulapp.ui.authorization.passwordreset

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_password_reset_completed.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateUp

class PasswordResetCompletedFragment : BaseFragment(R.layout.fragment_password_reset_completed) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        animationView.setOnClickListener {
            if (!animationView.isAnimating) {
                animationView.playAnimation()
            }
        }

        goToLoginFragmentButton.setOnClickListener {
            navigateUp()
        }
    }
}
