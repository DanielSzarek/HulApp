package pl.kamilszustak.hulapp.domain.mapper

abstract class Mapper<T, R> {
    abstract fun map(model: T): R

    fun mapAll(models: Iterable<T>): List<R> = models.map { model ->
        this.map(model)
    }

    inline fun onMap(model: T, action: (R) -> Unit) {
        val mapped = this.map(model)
        action(mapped)
    }

    inline fun onMapAll(models: Iterable<T>, action: (List<R>) -> Unit) {
        val mapped = this.mapAll(models)
        action(mapped)
    }

    inline fun onElementMap(models: Iterable<T>, action: (R) -> Unit) {
        this.mapAll(models).forEach { model ->
            action(model)
        }
    }

    inline fun mapIf(model: T, condition: (T) -> Boolean): R? {
        return if (condition(model)) {
            this.map(model)
        } else {
            null
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    inline fun mapElementsIf(models: Iterable<T>, condition: (T) -> Boolean): List<R> {
        return buildList {
            models.forEach { model ->
                if (condition(model)) {
                    val mapped = this@Mapper.map(model)
                    this.add(mapped)
                }
            }
        }
    }

    inline fun mapAllIf(models: Iterable<T>, condition: (Iterable<T>) -> Boolean): List<R>? {
        return if (condition(models)) {
            this.mapAll(models)
        } else {
            null
        }
    }
}