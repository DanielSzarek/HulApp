package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.data.model.SearchPrompt

@Dao
interface SearchPromptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prompt: SearchPrompt): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prompts: Collection<SearchPrompt>): List<Long>

    @Update
    suspend fun update(prompt: SearchPrompt)

    @Delete
    suspend fun delete(prompt: SearchPrompt)

    @Query("DELETE FROM search_prompts")
    suspend fun deleteAll()

    @Query("SELECT * FROM search_prompts")
    fun getAll(): Flow<List<SearchPrompt>>

    @Query("SELECT * FROM search_prompts WHERE LOWER(text) LIKE '%' || LOWER(:text) || '%'")
    fun getAllByTextContaining(text: String): Flow<List<SearchPrompt>>
}