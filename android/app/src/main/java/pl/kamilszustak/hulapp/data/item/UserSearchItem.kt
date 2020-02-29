package pl.kamilszustak.hulapp.data.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.item_user_search.view.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.util.load

class UserSearchItem(user: User) : ModelAbstractItem<User, UserSearchItem.ViewHolder>(user) {

    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int = R.id.fastadapter_user_search_item_id

    override val layoutRes: Int = R.layout.item_user_search

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<UserSearchItem>(view) {
        private val profilePhotoImageView: ImageView = view.profilePhotoImageView
        private val nameTextView: TextView = view.nameTextView

        override fun bindView(item: UserSearchItem, payloads: MutableList<Any>) {
            profilePhotoImageView.load(item.model.profilePhotoUrl)
            nameTextView.text = item.model.fullName
        }

        override fun unbindView(item: UserSearchItem) {
            profilePhotoImageView.setImageDrawable(null)
            nameTextView.text = null
        }
    }
}