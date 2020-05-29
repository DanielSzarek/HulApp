package pl.kamilszustak.hulapp.domain.usecase.post

import pl.kamilszustak.hulapp.data.repository.post.PostRepository
import pl.kamilszustak.hulapp.domain.model.network.AddPostRequstBody
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddPostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : AddPostUseCase {

    override suspend fun invoke(requestBody: AddPostRequstBody): Result<Unit> = withIOContext {
        postRepository.add(requestBody)
    }
}