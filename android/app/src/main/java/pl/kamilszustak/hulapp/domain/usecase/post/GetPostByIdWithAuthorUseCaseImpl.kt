package pl.kamilszustak.hulapp.domain.usecase.post

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.post.PostRepository
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPostByIdWithAuthorUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetPostByIdWithAuthorUseCase {

    override fun invoke(postId: Long): Flow<Resource<PostWithAuthor>> =
        postRepository.getByIdWithAuthor(postId)
}