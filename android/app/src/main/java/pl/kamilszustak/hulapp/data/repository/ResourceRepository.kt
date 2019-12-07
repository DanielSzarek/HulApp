package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource

interface ResourceRepository<T> {

    suspend fun insert(item: T) {
    }

    suspend fun insertAll(items: List<T>) {
    }

    suspend fun update(item: T) {
    }

    suspend fun delete(item: T) {
    }

    fun getAll(): Flow<Resource<List<T>>>

    fun getById(id: Long): Flow<Resource<T>>
}