package pl.kamilszustak.hulapp.di.module

import dagger.Binds
import dagger.Module
import pl.kamilszustak.hulapp.domain.usecase.post.*

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetAllPostsWithAuthorsUseCase(getAllPostsWithAuthorsUseCaseImpl: GetAllPostsWithAuthorsUseCaseImpl): GetAllPostsWithAuthorsUseCase

    @Binds
    abstract fun bindAddPostUseCase(addPostUseCaseImpl: AddPostUseCaseImpl): AddPostUseCase

    @Binds
    abstract fun bindGetPostByIdWithAuthorUseCase(getPostByIdWithAuthorUseCaseImpl: GetPostByIdWithAuthorUseCaseImpl): GetPostByIdWithAuthorUseCase
}