package pl.kamilszustak.hulapp.ui.main.profile.main.photo.options

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import pl.kamilszustak.hulapp.util.withIOContext
import java.io.File
import javax.inject.Inject

class ProfilePhotoOptionsViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : StateViewModel(application) {

    private val _uploadCompleted: SingleLiveData<Unit> = SingleLiveData()
    val uploadCompleted: LiveData<Unit> = _uploadCompleted

    fun uploadPhoto(file: File) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.setValue(false)

            val result = withIOContext {
                userRepository.uploadProfilePhoto(file)
            }

            if (result.isSuccess) {
                _uploadCompleted.call()
            } else {
                _errorEvent.value = R.string.changing_profile_photo_error_message
            }

            _isLoading.setValue(true)
        }
    }

    fun onDeleteProfilePhotoButtonClick() {

    }
}