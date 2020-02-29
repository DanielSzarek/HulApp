package pl.kamilszustak.hulapp.ui.main.profile.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.SearchPrompt
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.data.repository.searchprompt.SearchPromptRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val searchPromptRepository: SearchPromptRepository
) : BaseViewModel(application) {

    private val _adapterType: UniqueLiveData<AdapterType> = UniqueLiveData()
    val adapterType: LiveData<AdapterType> = _adapterType

    val searchPrompts: LiveData<List<SearchPrompt>> = searchPromptRepository.getAll()
        .asLiveData(viewModelScope.coroutineContext)

    val usersResource: ResourceDataSource<List<User>> = ResourceDataSource()

    init {
        _adapterType.value = AdapterType.SEARCH_PROMPTS
    }

    fun onQueryTextChange(text: String?) {
        if (text.isNullOrBlank()) {
            _adapterType.value = AdapterType.SEARCH_PROMPTS
        }
    }

    fun onQueryTextSubmit(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchPromptRepository.add(SearchPrompt(text))
        }

        _adapterType.value = AdapterType.USERS

        usersResource.changeFlowSource {
            userRepository.searchFor(text)
        }
    }

    enum class AdapterType {
        SEARCH_PROMPTS,
        USERS
    }
}