package pl.kamilszustak.hulapp.ui.main.feed.post.add

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.domain.model.network.AddPostRequstBody
import pl.kamilszustak.hulapp.domain.usecase.post.AddPostUseCase
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject

class AddPostViewModel @Inject constructor(
    application: Application,
    private val addPostUseCase: AddPostUseCase
) : BaseViewModel(application) {

    val postContentField: FormField<String> = formField {
        +Rule<String>("Treść postu nie może być pusta") {
            !it.isBlank()
        }
    }

    private val _completed: SingleLiveData<Unit> = SingleLiveData()
    val completed: LiveData<Unit> = _completed

    private val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: SingleLiveData<Int> = SingleLiveData()
    val error: LiveData<Int> = _error

    fun onAddPostButtonClick() {
        val content = postContentField.data.value?.trim()
        if (content.isNullOrBlank()) {
            _error.value = R.string.empty_post_error_message
            return
        }

        val requestBody = AddPostRequstBody(content)

        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            val result = withIOContext {
                addPostUseCase(requestBody)
            }

            result.onSuccess {
                _completed.call()
            }.onFailure {
                _error.value = R.string.adding_post_error_message
            }

            _isLoading.value = false
        }
    }
}