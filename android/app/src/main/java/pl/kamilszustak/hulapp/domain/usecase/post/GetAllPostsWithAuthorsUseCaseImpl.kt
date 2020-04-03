package pl.kamilszustak.hulapp.domain.usecase.post

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.post.PostRepository
import pl.kamilszustak.hulapp.data.repository.post.PostsSortOrder
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthorEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllPostsWithAuthorsUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetAllPostsWithAuthorsUseCase {

    override fun invoke(sortOrder: PostsSortOrder): Flow<Resource<List<PostWithAuthorEntity>>> =
        postRepository.getAllWithAuthors(sortOrder)
}