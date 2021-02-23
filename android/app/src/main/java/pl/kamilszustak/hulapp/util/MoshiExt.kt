package pl.kamilszustak.hulapp.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

inline fun <reified T> Moshi.adapter(): JsonAdapter<T> =
    this.adapter(T::class.java)