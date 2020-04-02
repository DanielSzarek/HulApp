package pl.kamilszustak.hulapp.di.module

import dagger.Binds
import dagger.Module
import pl.kamilszustak.hulapp.domain.usecase.post.AddPostUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.AddPostUseCaseImpl
import pl.kamilszustak.hulapp.domain.usecase.post.GetAllPostsWithAuthorsUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.GetAllPostsWithAuthorsUseCaseImpl

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetAllPostsWithAuthorsUseCase(getAllPostsWithAuthorsUseCaseImpl: GetAllPostsWithAuthorsUseCaseImpl): GetAllPostsWithAuthorsUseCase

    @Binds
    abstract fun bindAddPostUseCase(addPostUseCaseImpl: AddPostUseCaseImpl): AddPostUseCase
}