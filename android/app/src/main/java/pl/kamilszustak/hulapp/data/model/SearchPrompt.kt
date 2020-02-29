package pl.kamilszustak.hulapp.data.model

import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "search_prompts")
@Parcelize
data class SearchPrompt(
    val text: String
) : DatabaseEntity()