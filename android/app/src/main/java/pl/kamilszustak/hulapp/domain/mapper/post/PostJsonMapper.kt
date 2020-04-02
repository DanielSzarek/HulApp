package pl.kamilszustak.hulapp.domain.mapper.post

import pl.kamilszustak.hulapp.domain.mapper.JsonModelMapper
import pl.kamilszustak.hulapp.domain.model.post.PostEntity
import pl.kamilszustak.hulapp.domain.model.post.PostJson
import javax.inject.Inject

class PostJsonMapper @Inject constructor() : JsonModelMapper<PostJson, PostEntity>() {
    override fun map(model: PostJson): PostEntity = PostEntity(
        authorId = model.author.id,
        content = model.content
    ).apply {
        this.id = model.id
        this.createdAt = model.createdAt
    }
}