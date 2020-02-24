package pl.kamilszustak.hulapp.common.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.util.*

class FormField<T>(
    rule: Rule<T>? = null
) {

    val rules: MutableList<Rule<T>> = mutableListOf()

    val data: UniqueLiveData<T> = UniqueLiveData()

    private val invalidRule: LiveData<Rule<T>?> = data.map { value ->
        rules.firstOrNull { rule ->
            !rule.isValid(value)
        }
    }

    val isValid: LiveData<Boolean> = invalidRule.map {
        it == null
    }

    val errorMessage: LiveData<String> = invalidRule.mapNotNull {
        it?.errorMessage
    }

    var isNullable = false

    init {
        if (rule != null) {
            rules.add(rule)
        }
    }

    operator fun Rule<T>.unaryPlus() {
        rules.add(this)
    }

    operator fun Rule<T>.unaryMinus() {
        rules.remove(this)
    }

    fun isValid(): Boolean {
        val value = this.data.value

        return isNullable || (value != null && rules.all {
            it.isValid(value)
        })
    }

    companion object {
        fun validateFields(vararg fields: FormField<*>): LiveData<Boolean> {
            val result = MediatorLiveData<Boolean>()
            val sources = mutableListOf<LiveData<Boolean>>()

            fields.forEach {
                sources.add(it.isValid)
            }

            result.addSources(sources) {
                val allValid = fields.all { field ->
                    val value = field.isValid.value
                    field.isNullable || (value != null && value)
                }
                result.value = allValid
            }

            return result
        }
    }
}

inline fun <T> formField(function: FormField<T>.() -> Unit): FormField<T> {
    return FormField<T>().apply {
        function()
    }
}