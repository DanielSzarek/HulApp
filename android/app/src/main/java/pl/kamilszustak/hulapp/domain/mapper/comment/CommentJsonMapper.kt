package pl.kamilszustak.hulapp.domain.mapper.comment

import pl.kamilszustak.hulapp.domain.mapper.Mapper
import pl.kamilszustak.hulapp.domain.model.comment.CommentEntity
import pl.kamilszustak.hulapp.domain.model.comment.CommentJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentJsonMapper @Inject constructor() : Mapper<CommentJson, CommentEntity>() {
    override fun map(model: CommentJson): CommentEntity = CommentEntity(
        postId = model.postId,
        editDate = model.editDate,
        authorId = model.author.id,
        content = model.content
    ).apply {
        this.id = model.id
        this.creationDate = model.createdAt
    }
}