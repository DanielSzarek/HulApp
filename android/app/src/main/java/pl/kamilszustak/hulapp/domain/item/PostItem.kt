package pl.kamilszustak.hulapp.domain.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.BindingViewHolder
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.ItemPostsListBinding
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor

class PostItem(postWithAuthor: PostWithAuthor) : ModelAbstractBindingItem<PostWithAuthor, ItemPostsListBinding>(postWithAuthor) {
    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int = R.id.fastadapter_post_item

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemPostsListBinding =
        ItemPostsListBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemPostsListBinding, payloads: List<Any>) {
        binding.postWithAuthor = this.model
    }

    override fun unbindView(binding: ItemPostsListBinding) {
        with(binding) {
            postWithAuthor = null
            executePendingBindings()
        }
    }

    override fun getViewHolder(viewBinding: ItemPostsListBinding): BindingViewHolder<ItemPostsListBinding> =
        ViewHolder(viewBinding)

    inner class ViewHolder(binding: ItemPostsListBinding) : BindingViewHolder<ItemPostsListBinding>(binding)
}