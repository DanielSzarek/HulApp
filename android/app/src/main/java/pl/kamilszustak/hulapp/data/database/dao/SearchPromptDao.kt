package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.SearchPrompt

@Dao
interface SearchPromptDao : BaseDao<SearchPrompt> {
    @Transaction
    suspend fun insertAndDeleteDuplicates(prompt: SearchPrompt): Long {
        deleteAllWhereTextEquals(prompt.text)
        return insert(prompt)
    }

    @Query("DELETE FROM SEARCH_PROMPTS WHERE text = :text")
    suspend fun deleteAllWhereTextEquals(text: String)

    @Query("DELETE FROM search_prompts WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM search_prompts")
    suspend fun deleteAll()

    @Query("SELECT * FROM search_prompts WHERE id = :id")
    fun getById(id: Long): Flow<List<SearchPrompt>>

    @Query("SELECT * FROM search_prompts")
    fun getAll(): Flow<List<SearchPrompt>>

    @Query("SELECT * FROM search_prompts ORDER BY date ASC")
    fun getAllOrderedByDateAscending(): Flow<List<SearchPrompt>>

    @Query("SELECT * FROM search_prompts ORDER BY date DESC")
    fun getAllOrderedByDateDescending(): Flow<List<SearchPrompt>>

    @Query("SELECT * FROM search_prompts WHERE LOWER(text) LIKE '%' || LOWER(:text) || '%'")
    fun getAllByTextContaining(text: String): Flow<List<SearchPrompt>>
}