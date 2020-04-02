package pl.kamilszustak.hulapp.data.repository.searchprompt

import kotlinx.coroutines.flow.*
import pl.kamilszustak.hulapp.data.database.dao.SearchPromptDao
import pl.kamilszustak.hulapp.domain.model.SearchPrompt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchPromptRepository @Inject constructor(
    private val searchPromptDao: SearchPromptDao
) {
    suspend fun add(prompt: SearchPrompt) {
        searchPromptDao.insertAndDeleteDuplicates(prompt)
    }

    suspend fun deleteById(id: Long) {
        searchPromptDao.deleteById(id)
    }

    suspend fun deleteAll() {
        searchPromptDao.deleteAll()
    }

    suspend fun addAll(prompts: List<SearchPrompt>) {
        searchPromptDao.insertAll(prompts)
    }

    fun getAll(sortOrder: SearchPromptSortOrder = SearchPromptSortOrder.DATE_DESCENDING): Flow<List<SearchPrompt>> {
        return when (sortOrder) {
            SearchPromptSortOrder.DATE_ASCENDING -> searchPromptDao.getAllOrderedByDateAscending()
            SearchPromptSortOrder.DATE_DESCENDING -> searchPromptDao.getAllOrderedByDateDescending()
        }
    }
}