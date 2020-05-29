package pl.kamilszustak.hulapp.domain.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.ItemCommentsListBinding
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor

class CommentItem(commentWithAuthor: CommentWithAuthor) : ModelAbstractBindingItem<CommentWithAuthor, ItemCommentsListBinding>(commentWithAuthor) {
    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int = R.id.commentButton

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemCommentsListBinding =
        ItemCommentsListBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemCommentsListBinding, payloads: List<Any>) {
        binding.commentWithAuthor = this.model
    }

    override fun unbindView(binding: ItemCommentsListBinding) {
        with(binding) {
            commentWithAuthor = null
            executePendingBindings()
        }
    }
}