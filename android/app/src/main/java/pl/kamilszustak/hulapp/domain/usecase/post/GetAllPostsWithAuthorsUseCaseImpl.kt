package pl.kamilszustak.hulapp.domain.usecase.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.post.PostRepository
import pl.kamilszustak.hulapp.data.repository.post.PostsSortOrder
import pl.kamilszustak.hulapp.domain.mapper.post.PostWithAuthorEntityMapper
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllPostsWithAuthorsUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository,
    private val mapper: PostWithAuthorEntityMapper
) : GetAllPostsWithAuthorsUseCase {

    override fun invoke(sortOrder: PostsSortOrder): Flow<Resource<List<PostWithAuthor>>> =
        postRepository.getAllWithAuthors(sortOrder).map { resource ->
            resource.mapData { posts ->
                mapper.mapAll(posts)
            }
        }
}