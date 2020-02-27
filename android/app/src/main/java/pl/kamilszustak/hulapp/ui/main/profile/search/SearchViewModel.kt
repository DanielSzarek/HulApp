package pl.kamilszustak.hulapp.ui.main.profile.search

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : BaseViewModel(application) {

    val inputText: UniqueLiveData<String> = UniqueLiveData()

    val usersResource: ResourceDataSource<List<User>> = ResourceDataSource()

    init {
        usersResource.result.addSource(inputText) { text ->
            if (text != null) {
                usersResource.changeFlowSource {
                    userRepository.searchFor(text)
                }
            }
        }
    }
}