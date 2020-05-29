package pl.kamilszustak.hulapp.util

import androidx.recyclerview.widget.DiffUtil
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

fun <A : ModelAdapter<Model, Item>, Model, Item : GenericItem> FastAdapterDiffUtil.calculateDiff(
    adapter: A,
    models: List<Model>
): DiffUtil.DiffResult {
    val items = adapter.intercept(models)

    return this.calculateDiff(adapter, items)
}

fun <Model, Item : GenericItem> ModelAdapter<Model, Item>.updateModels(models: List<Model>) {
    val result = FastAdapterDiffUtil.calculateDiff(this, models)
    FastAdapterDiffUtil.set(this, result)
    this.fastAdapter?.notifyAdapterDataSetChanged()
}