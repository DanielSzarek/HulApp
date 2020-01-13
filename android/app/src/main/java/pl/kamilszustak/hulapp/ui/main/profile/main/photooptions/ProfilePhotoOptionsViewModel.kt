package pl.kamilszustak.hulapp.ui.main.profile.main.photooptions

import android.app.Application
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import java.io.File
import javax.inject.Inject

class ProfilePhotoOptionsViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : BaseViewModel(application) {

    fun uploadPhoto(file: File) {

    }

    fun onDeleteProfilePhotoButtonClick() {

    }
}