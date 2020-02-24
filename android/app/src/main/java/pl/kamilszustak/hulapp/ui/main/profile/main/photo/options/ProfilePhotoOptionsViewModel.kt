package pl.kamilszustak.hulapp.ui.main.profile.main.photo.options

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.withIoContext
import java.io.File
import javax.inject.Inject

class ProfilePhotoOptionsViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : BaseViewModel(application) {

    private val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _uploadCompleted: SingleLiveData<Unit> = SingleLiveData()
    val uploadCompleted: LiveData<Unit> = _uploadCompleted

    private val _uploadError: SingleLiveData<String> = SingleLiveData()
    val uploadError: LiveData<String> = _uploadError

    fun uploadPhoto(file: File) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.setValue(false)

            val result = withIoContext {
                userRepository.uploadProfilePhoto(file)
            }

            if (result.isSuccess) {
                _uploadCompleted.call()
            } else {
                _uploadError.value = "Wystąpił błąd podczas zmiany zdjęcia profilowego"
            }

            _isLoading.setValue(true)
        }
    }

    fun onDeleteProfilePhotoButtonClick() {

    }
}