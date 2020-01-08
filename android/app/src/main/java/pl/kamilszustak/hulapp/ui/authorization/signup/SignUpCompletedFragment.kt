package pl.kamilszustak.hulapp.ui.authorization.signup

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_sign_up_completed.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateUp

class SignUpCompletedFragment : BaseFragment(R.layout.fragment_sign_up_completed) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        goToLoginFragmentButton.setOnClickListener {
            navigateUp()
        }

        animationView.setOnClickListener {
            if (!animationView.isAnimating) {
                animationView.playAnimation()
            }
        }
    }
}