package pl.kamilszustak.hulapp.domain.item

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.item_search_prompts_list.view.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.domain.model.SearchPrompt

class SearchPromptItem(searchPrompt: SearchPrompt) : ModelAbstractItem<SearchPrompt, SearchPromptItem.ViewHolder>(searchPrompt) {

    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int = R.id.fastadapter_search_prompt_item

    override val layoutRes: Int = R.layout.item_search_prompts_list

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<SearchPromptItem>(view) {
        private val searchPromptTextView: TextView = view.searchPromptTextView
        val deleteButton: ImageButton = view.deleteButton

        override fun bindView(item: SearchPromptItem, payloads: List<Any>) {
            searchPromptTextView.text = item.model.text
        }

        override fun unbindView(item: SearchPromptItem) {
            searchPromptTextView.text = null
        }
    }
}