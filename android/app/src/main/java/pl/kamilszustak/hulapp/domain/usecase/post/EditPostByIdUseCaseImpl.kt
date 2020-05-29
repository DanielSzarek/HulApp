package pl.kamilszustak.hulapp.domain.usecase.post

import pl.kamilszustak.hulapp.data.repository.post.PostRepository
import pl.kamilszustak.hulapp.domain.model.network.EditPostRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditPostByIdUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : EditPostByIdUseCase {

    override suspend fun invoke(postId: Long, requestBody: EditPostRequestBody): Result<Unit> =
        postRepository.editById(postId, requestBody)
}