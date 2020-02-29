package pl.kamilszustak.hulapp.data.repository

import pl.kamilszustak.hulapp.data.database.dao.SearchPromptDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchPromptRepository @Inject constructor(
    private val searchPromptDao: SearchPromptDao
) {
}