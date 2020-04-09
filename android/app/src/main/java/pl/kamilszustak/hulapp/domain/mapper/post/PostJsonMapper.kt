package pl.kamilszustak.hulapp.domain.mapper.post

import pl.kamilszustak.hulapp.domain.mapper.Mapper
import pl.kamilszustak.hulapp.domain.model.post.PostEntity
import pl.kamilszustak.hulapp.domain.model.post.PostJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostJsonMapper @Inject constructor() : Mapper<PostJson, PostEntity>() {
    override fun map(model: PostJson): PostEntity = PostEntity(
        editDate = model.editDate,
        authorId = model.author.id,
        content = model.content
    ).apply {
        this.id = model.id
        this.creationDate = model.createdAt
    }
}