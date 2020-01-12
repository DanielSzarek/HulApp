package pl.kamilszustak.hulapp.common.form

fun Iterable<FormField<*>>.validate(): Boolean {
    return this.all { field ->
        field.isValid()
    }
}