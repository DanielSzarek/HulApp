package pl.kamilszustak.hulapp.ui.main.profile.main.photo.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.dialog_profile_photo_fullscreen.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.DialogProfilePhotoFullscreenBinding
import pl.kamilszustak.hulapp.ui.base.BaseDialogFragment
import pl.kamilszustak.hulapp.util.navigateUp

class ProfilePhotoFullscreenDialogFragment : BaseDialogFragment(R.layout.dialog_profile_photo_fullscreen) {

    private val args: ProfilePhotoFullscreenDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<DialogProfilePhotoFullscreenBinding>(
            inflater,
            R.layout.dialog_profile_photo_fullscreen,
            container,
            false
        ).apply {
            this.profilePhotoUrl = args.profilePhotoUrl
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        closeButton.setOnClickListener {
            navigateUp()
        }
    }

    companion object {
        const val sharedElementTransitionName: String = "PROFILE_PHOTO_FULLSCREEN_TRANSITION"
    }
}