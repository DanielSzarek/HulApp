package pl.kamilszustak.hulapp.domain.usecase.post

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthorEntity

interface GetPostByIdWithAuthorUseCase {
    operator fun invoke(postId: Long): Flow<Resource<PostWithAuthorEntity>>
}