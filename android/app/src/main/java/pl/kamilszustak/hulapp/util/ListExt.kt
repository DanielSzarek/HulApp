package pl.kamilszustak.hulapp.util

infix fun <T> Any?.inside(list: List<T>): Boolean =
    list.contains(this)