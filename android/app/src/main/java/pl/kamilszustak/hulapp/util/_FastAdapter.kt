package pl.kamilszustak.hulapp.util

import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil

fun <A : ModelAdapter<Model, Item>, Model, Item : GenericItem> FastAdapterDiffUtil.set(
    adapter: A,
    models: List<Model>
): A {
    val items = adapter.intercept(models)
    return this.set(adapter, items)
}