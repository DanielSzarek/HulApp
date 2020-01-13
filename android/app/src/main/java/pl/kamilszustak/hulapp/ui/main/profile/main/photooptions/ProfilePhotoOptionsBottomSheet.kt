package pl.kamilszustak.hulapp.ui.main.profile.main.photooptions

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.bottom_sheet_profile_photo_options.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.ui.base.BaseBottomSheetDialogFragment
import javax.inject.Inject

class ProfilePhotoOptionsBottomSheet : BaseBottomSheetDialogFragment(R.layout.bottom_sheet_profile_photo_options) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: ProfilePhotoOptionsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        changeProfilePhotoLayout.setOnClickListener {
            openImagePicker()
        }

        deleteProfilePhotoLayout.setOnClickListener {
            viewModel.onDeleteProfilePhotoButtonClick()
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .cropSquare()
            .compress(1024)
            .start { resultCode, data ->
                if (resultCode == Activity.RESULT_OK) {
                    val file = ImagePicker.getFile(data)
                    if (file != null) {
                        viewModel.uploadPhoto(file)
                    }
                }
            }
    }

    companion object {
        const val tag: String = "PROFILE_PHOTO_OPTIONS_BOTTOM_SHEET"

        fun getInstance(): ProfilePhotoOptionsBottomSheet =
            ProfilePhotoOptionsBottomSheet()
    }
}