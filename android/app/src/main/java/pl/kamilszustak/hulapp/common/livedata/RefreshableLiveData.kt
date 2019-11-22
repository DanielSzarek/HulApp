package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * LiveData stworzone do współpracy z NetworkBoundResource, które zwraca także LiveData.
 * Przy użyciu MutableLiveData obserwatorzy otrzymywaliby aktualizację dopóki nie pobralibyśmy nowych danych,
 * gdyż wtedy NetworkBoundResource zwraca nowy obiekt LiveData, który nie posiada obserwatorów.
 * Natomiast w tym przypadku obserwowany obiekt jest zawsze ten sam, zmienia się tylko obiekt źródła.
 */
open class RefreshableLiveData<T>(
    private var block: () -> LiveData<T> = { UniqueLiveData<T>() }
) : MediatorLiveData<T>() {

    private var data: LiveData<T> = UniqueLiveData()

    init {
        getDataFromSource()
    }

    fun refresh() {
        this.removeSource(data)
        getDataFromSource()
    }

    private fun getDataFromSource() {
        data = block()
        this.addSource(data) {
            this.value = it
        }
    }

    fun changeDataSource(block: () -> LiveData<T>) {
        this.block = block
        refresh()
    }
}