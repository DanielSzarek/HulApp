package pl.kamilszustak.hulapp.domain.mapper.post

import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.domain.mapper.Mapper
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthorEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostWithAuthorEntityMapper @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) : Mapper<PostWithAuthorEntity, PostWithAuthor>() {

    override fun map(model: PostWithAuthorEntity): PostWithAuthor {
        TODO("Not yet implemented")
    }
}