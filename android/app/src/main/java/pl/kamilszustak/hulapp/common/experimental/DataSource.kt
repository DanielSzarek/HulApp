package pl.kamilszustak.hulapp.common.experimental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData

open class DataSource<T>(
    source: (() -> Flow<T>)? = null
) {
    private var block: () -> LiveData<T>
    private var sourceData: LiveData<T> = UniqueLiveData()
    val data: MediatorLiveData<T> = MediatorLiveData()

    init {
        this.block = if (source != null) {
            { source().asLiveData() }
        } else {
            { UniqueLiveData() }
        }

        getSourceData()
    }

    private fun getSourceData() {
        sourceData = block()
        data.addSource(sourceData) {
            data.value = it
        }
    }

    fun refresh() {
        data.removeSource(sourceData)
        getSourceData()
    }

    fun setLiveDataSource(block: () -> LiveData<T>) {
        this.block = block
        refresh()
    }

    fun setFlowSource(block: () -> Flow<T>) {
        setLiveDataSource {
            block().asLiveData()
        }
    }
}