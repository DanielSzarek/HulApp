package pl.kamilszustak.hulapp.ui.main.profile.main.photo.options

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.github.dhaval2404.imagepicker.ImagePicker
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.binding.view.viewBinding
import pl.kamilszustak.hulapp.databinding.BottomSheetProfilePhotoOptionsBinding
import pl.kamilszustak.hulapp.ui.base.BaseBottomSheetDialogFragment
import pl.kamilszustak.hulapp.util.navigateUp
import javax.inject.Inject

class ProfilePhotoOptionsBottomSheet : BaseBottomSheetDialogFragment(R.layout.bottom_sheet_profile_photo_options) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: ProfilePhotoOptionsViewModel by viewModels {
        viewModelFactory
    }

    private val binding: BottomSheetProfilePhotoOptionsBinding by viewBinding(BottomSheetProfilePhotoOptionsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModel()
    }

    private fun setListeners() {
        binding.changeProfilePhotoLayout.setOnClickListener {
            openImagePicker()
        }

        binding.deleteProfilePhotoLayout.setOnClickListener {
            viewModel.onDeleteProfilePhotoButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            this.isCancelable = !isLoading
        }

        viewModel.uploadCompleted.observe(viewLifecycleOwner) {
            navigateUp()
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
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