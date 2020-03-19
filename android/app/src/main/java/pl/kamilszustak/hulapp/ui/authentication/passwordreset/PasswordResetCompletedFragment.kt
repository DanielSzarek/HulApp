package pl.kamilszustak.hulapp.ui.authentication.passwordreset

import android.os.Bundle
import android.view.View
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.binding.view.viewBinding
import pl.kamilszustak.hulapp.databinding.FragmentPasswordResetCompletedBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateUp

class PasswordResetCompletedFragment : BaseFragment(R.layout.fragment_password_reset_completed) {

    private val binding: FragmentPasswordResetCompletedBinding by viewBinding(FragmentPasswordResetCompletedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.animationView.setOnClickListener {
            if (!binding.animationView.isAnimating) {
                binding.animationView.playAnimation()
            }
        }

        binding.goToLoginFragmentButton.setOnClickListener {
            navigateUp()
        }
    }
}
