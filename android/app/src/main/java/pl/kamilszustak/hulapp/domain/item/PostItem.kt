package pl.kamilszustak.hulapp.domain.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.date.DateFormats
import pl.kamilszustak.hulapp.databinding.ItemPostsListBinding
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.util.load

class PostItem(postWithAuthor: PostWithAuthor) : ModelAbstractBindingItem<PostWithAuthor, ItemPostsListBinding>(postWithAuthor) {
    override var identifier: Long
        get() = this.model.post.id
        set(value) {}

    override val type: Int = R.id.fastadapter_post_item

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemPostsListBinding =
        ItemPostsListBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemPostsListBinding, payloads: List<Any>) {
        with(binding) {
            authorProfilePhotoImageView.load(model.author.profilePhotoUrl)
            authorFullNameTextView.text = model.author.fullName
            dateTextView.text = DateFormats.dateFormat.format(model.post.createdAt)
            contentTextView.text = model.post.content
        }
    }

    override fun unbindView(binding: ItemPostsListBinding) {
        with(binding) {
            authorProfilePhotoImageView.setImageDrawable(null)
            authorFullNameTextView.text = null
            dateTextView.text = null
            contentTextView.text = null
        }
    }
}