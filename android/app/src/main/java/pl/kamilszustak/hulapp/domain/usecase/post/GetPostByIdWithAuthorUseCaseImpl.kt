package pl.kamilszustak.hulapp.domain.usecase.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.post.PostRepository
import pl.kamilszustak.hulapp.domain.mapper.post.PostWithAuthorEntityMapper
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPostByIdWithAuthorUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository,
    private val mapper: PostWithAuthorEntityMapper
) : GetPostByIdWithAuthorUseCase {

    override fun invoke(postId: Long, shouldFetch: Boolean): Flow<Resource<PostWithAuthor>> =
        postRepository.getByIdWithAuthor(postId, shouldFetch).map { resource ->
            resource.mapData { post ->
                mapper.map(post)
            }
        }
}