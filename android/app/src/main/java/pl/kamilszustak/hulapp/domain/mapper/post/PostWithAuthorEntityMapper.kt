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

    val myId: Long = userDetailsRepository.getValue(UserDetailsRepository.UserDetailsKey.USER_ID)

    override fun map(model: PostWithAuthorEntity): PostWithAuthor = PostWithAuthor(
        id = model.post.id,
        creationDate = model.post.creationDate,
        editDate = model.post.editDate,
        content = model.post.content,
        author = model.author,
        isMine = (model.author.id == myId)
    )
}