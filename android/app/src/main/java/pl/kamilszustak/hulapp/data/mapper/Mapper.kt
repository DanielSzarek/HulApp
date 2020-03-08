package pl.kamilszustak.hulapp.data.mapper

interface Mapper<T, R> {
    fun map(model: T): R
}