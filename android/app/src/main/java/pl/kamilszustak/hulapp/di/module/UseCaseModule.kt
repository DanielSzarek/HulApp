package pl.kamilszustak.hulapp.di.module

import dagger.Binds
import dagger.Module
import pl.kamilszustak.hulapp.domain.usecase.comment.*
import pl.kamilszustak.hulapp.domain.usecase.point.AddMapPointUseCase
import pl.kamilszustak.hulapp.domain.usecase.point.AddMapPointUseCaseImpl
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
    abstract fun bindEditPostUseCase(useCaseImpl: EditPostByIdUseCaseImpl): EditPostByIdUseCase

    @Binds
    abstract fun bindDeletePostByIdUseCase(useCaseImpl: DeletePostByIdUseCaseImpl): DeletePostByIdUseCase

    @Binds
    abstract fun bindGetAllCommentsWithAuthorsByPostIdUseCase(useCaseImpl: GetAllCommentsWithAuthorsByPostIdUseCaseImpl): GetAllCommentsWithAuthorsByPostIdUseCase

    @Binds
    abstract fun bindGetCommentByIdWithAuthorUseCase(useCaseImpl: GetCommentByIdWithAuthorUseCaseImpl): GetCommentByIdWithAuthorUseCase

    @Binds
    abstract fun bindAddCommentUseCase(useCaseImpl: AddCommentUseCaseImpl): AddCommentUseCase

    @Binds
    abstract fun bindEditCommentByIdUseCase(useCaseImpl: EditCommentByIdUseCaseImpl): EditCommentByIdUseCase

    @Binds
    abstract fun bindDeleteCommentByIdUseCase(useCaseImpl: DeleteCommentByIdUseCaseImpl): DeleteCommentByIdUseCase

    @Binds
    abstract fun bindAddMapPointUseCase(useCaseImpl: AddMapPointUseCaseImpl): AddMapPointUseCase
}