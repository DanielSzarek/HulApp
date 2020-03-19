package pl.kamilszustak.hulapp.ui.main.profile.main.photo.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.DialogProfilePhotoFullscreenBinding
import pl.kamilszustak.hulapp.ui.base.BaseDialogFragment
import pl.kamilszustak.hulapp.util.navigateUp

class ProfilePhotoFullscreenDialogFragment : BaseDialogFragment() {

    private val args: ProfilePhotoFullscreenDialogFragmentArgs by navArgs()

    private lateinit var binding: DialogProfilePhotoFullscreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<DialogProfilePhotoFullscreenBinding>(
            inflater,
            R.layout.dialog_profile_photo_fullscreen,
            container,
            false
        ).apply {
            this.profilePhotoUrl = args.profilePhotoUrl
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding. closeButton.setOnClickListener {
            navigateUp()
        }
    }
}