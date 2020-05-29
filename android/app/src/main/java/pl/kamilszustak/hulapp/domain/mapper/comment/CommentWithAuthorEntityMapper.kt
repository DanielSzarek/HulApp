package pl.kamilszustak.hulapp.domain.mapper.comment

import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.domain.mapper.Mapper
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthorEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentWithAuthorEntityMapper @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) : Mapper<CommentWithAuthorEntity, CommentWithAuthor>() {

    private val myId: Long = userDetailsRepository.getValue(UserDetailsRepository.UserDetailsKey.USER_ID)

    override fun map(model: CommentWithAuthorEntity): CommentWithAuthor = CommentWithAuthor(
        id = model.comment.id,
        creationDate = model.comment.creationDate,
        editDate = model.comment.editDate,
        postId = model.comment.postId,
        author = model.author,
        content = model.comment.content,
        isMine = (model.comment.authorId == myId)
    )
}