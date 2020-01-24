package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

/**
 * Klasa stworzona do współpracy z NetworkBoundResource, które zwraca także LiveData.
 * Przy użyciu MutableLiveData obserwatorzy otrzymywaliby aktualizację dopóki nie pobralibyśmy nowych danych,
 * gdyż wtedy NetworkBoundResource zwraca nowy obiekt LiveData, który nie posiada obserwatorów.
 * Natomiast w tym przypadku obserwowany obiekt jest zawsze ten sam, zmienia się tylko obiekt źródła.
 */
open class RefreshableDataSource<T>(
    private var block: () -> LiveData<T> = { UniqueLiveData<T>() }
) {

    val result: MediatorLiveData<T> = MediatorLiveData()

    private var data: LiveData<T> = UniqueLiveData()

    init {
        getDataFromSource()
    }

    fun refresh() {
        result.removeSource(data)
        getDataFromSource()
    }

    private fun getDataFromSource() {
        data = block()
        result.addSource(data) {
            result.value = it
        }
    }

    fun changeLiveDataSource(block: () -> LiveData<T>) {
        this.block = block
        refresh()
    }

    fun changeFlowSource(block: () -> Flow<T>) {
        changeLiveDataSource {
            block().asLiveData()
        }
    }
}