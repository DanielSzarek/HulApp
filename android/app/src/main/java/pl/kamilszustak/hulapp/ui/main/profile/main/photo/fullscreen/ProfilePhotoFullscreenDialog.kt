package pl.kamilszustak.hulapp.ui.main.profile.main.photo.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.activity_main.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.DialogProfilePhotoFullscreenBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment

class ProfilePhotoFullscreenDialog : BaseFragment(R.layout.dialog_profile_photo_fullscreen) {

    private val args: ProfilePhotoFullscreenDialogArgs by navArgs()

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
            this.profilePhotoUrl = this@ProfilePhotoFullscreenDialog.args.profilePhotoUrl
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideActionBar()
    }

    override fun onDestroy() {
        super.onDestroy()

        showActionBar()
    }
}