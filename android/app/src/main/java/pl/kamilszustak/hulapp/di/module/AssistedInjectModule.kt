package pl.kamilszustak.hulapp.di.module

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@Module(
    includes = [AssistedInject_AssistedInjectModule::class]
)
@AssistedModule
interface AssistedInjectModule