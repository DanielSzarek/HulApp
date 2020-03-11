package pl.kamilszustak.hulapp.data.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.databinding.ItemUserSearchBinding
import pl.kamilszustak.hulapp.util.load

class UserSearchItem(user: User) : ModelAbstractBindingItem<User, ItemUserSearchBinding>(user) {

    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int = R.id.fastadapter_user_search_item_id

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemUserSearchBinding =
        ItemUserSearchBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemUserSearchBinding, payloads: List<Any>) {
        with(binding) {
            profilePhotoImageView.load(model.profilePhotoUrl)
            nameTextView.text = model.fullName
        }
    }

    override fun unbindView(binding: ItemUserSearchBinding) {
        with(binding) {
            profilePhotoImageView.setImageDrawable(null)
            nameTextView.text = null
        }
    }
}