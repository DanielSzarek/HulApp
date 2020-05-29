package pl.kamilszustak.hulapp.common.experimental

import kotlinx.coroutines.flow.Flow

class ResourceDataSource<T>(
    source: (() -> Flow<Resource<T>>)? = null
) : DataSource<Resource<T>>(source) {

//    val isLoading: LiveData<Boolean> = this.data.map { resource ->
//        resource.isLoading && resource.data == null
//    }
}