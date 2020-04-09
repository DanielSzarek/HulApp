package pl.kamilszustak.hulapp.di.module

import dagger.Binds
import dagger.Module
import pl.kamilszustak.hulapp.domain.usecase.post.*

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetAllPostsWithAuthorsUseCase(useCaseImpl: GetAllPostsWithAuthorsUseCaseImpl): GetAllPostsWithAuthorsUseCase

    @Binds
    abstract fun bindAddPostUseCase(useCaseImpl: AddPostUseCaseImpl): AddPostUseCase

    @Binds
    abstract fun bindGetPostByIdWithAuthorUseCase(useCaseImpl: GetPostByIdWithAuthorUseCaseImpl): GetPostByIdWithAuthorUseCase

    @Binds
    abstract fun bindDeletePostByIdUseCase(useCaseImpl: DeletePostByIdUseCaseImpl): DeletePostByIdUseCase
}