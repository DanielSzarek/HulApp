package pl.kamilszustak.hulapp.ui.main.profile.main.photooptions

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
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

    private val _isCancelable: UniqueLiveData<Boolean> = UniqueLiveData()
    val isCancelable: LiveData<Boolean> = _isCancelable

    private val _uploadCompleted: SingleLiveEvent<Unit> = SingleLiveEvent()
    val uploadCompleted: LiveData<Unit> = _uploadCompleted

    fun uploadPhoto(file: File) {
        viewModelScope.launch(Dispatchers.Main) {
            _isCancelable.setValue(false)

            val result = withIoContext {
                userRepository.uploadProfilePhoto(file)
            }

            _isCancelable.setValue(true)
            _uploadCompleted.call()
        }
    }

    fun onDeleteProfilePhotoButtonClick() {

    }
}