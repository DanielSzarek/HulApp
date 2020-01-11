package pl.kamilszustak.hulapp.util

fun CharSequence.containsAny(list: List<CharSequence>): Boolean {
    return list.any {
        this.contains(it)
    }
}