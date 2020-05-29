package pl.kamilszustak.hulapp.ui.authentication.signup

import android.os.Bundle
import android.view.View
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.binding.view.viewBinding
import pl.kamilszustak.hulapp.databinding.FragmentSignUpCompletedBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateUp

class SignUpCompletedFragment : BaseFragment(R.layout.fragment_sign_up_completed) {

    private val binding: FragmentSignUpCompletedBinding by viewBinding(FragmentSignUpCompletedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.goToLoginFragmentButton.setOnClickListener {
            navigateUp()
        }

        binding.animationView.setOnClickListener {
            if (!binding.animationView.isAnimating) {
                binding.animationView.playAnimation()
            }
        }
    }
}