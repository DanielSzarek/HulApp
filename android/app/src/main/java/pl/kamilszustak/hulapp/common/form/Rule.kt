package pl.kamilszustak.hulapp.common.form

data class Rule<T>(
    val errorMessage: String,
    val isValid: (T) -> Boolean
)