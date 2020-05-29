package pl.kamilszustak.hulapp.domain.usecase.post

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.post.PostsSortOrder
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor

interface GetAllPostsWithAuthorsUseCase {
    operator fun invoke(sortOrder: PostsSortOrder = PostsSortOrder.DATE_DESCENDING): Flow<Resource<List<PostWithAuthor>>>
}