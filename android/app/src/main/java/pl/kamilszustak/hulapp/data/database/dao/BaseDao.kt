package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<E> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: E): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entities: E): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: Collection<E>): List<Long>

    @Update
    suspend fun update(entity: E): Int

    @Update
    suspend fun update(vararg entities: E): Int

    @Update
    suspend fun updateAll(entities: Iterable<E>): Int

    @Delete
    suspend fun delete(entity: E): Int

    @Delete
    suspend fun delete(vararg entities: E): Int

    @Delete
    suspend fun deleteAll(entities: Iterable<E>): Int
}