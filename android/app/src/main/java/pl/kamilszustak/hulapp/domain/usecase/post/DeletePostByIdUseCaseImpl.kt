package pl.kamilszustak.hulapp.domain.usecase.post

import pl.kamilszustak.hulapp.data.repository.post.PostRepository
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeletePostByIdUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : DeletePostByIdUseCase {

    override suspend fun invoke(id: Long): Result<Unit> = withIOContext {
        postRepository.deleteById(id)
    }
}