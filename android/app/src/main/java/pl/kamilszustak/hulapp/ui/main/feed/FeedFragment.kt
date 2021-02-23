package pl.kamilszustak.hulapp.ui.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.toast
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentFeedBinding
import pl.kamilszustak.hulapp.domain.item.PostItem
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.*
import javax.inject.Inject

class FeedFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: FeedViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentFeedBinding
    private val modelAdapter: ModelAdapter<PostWithAuthor, PostItem> by lazy {
        ModelAdapter<PostWithAuthor, PostItem> { postWithAuthor ->
            PostItem(postWithAuthor)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFeedBinding>(
            inflater,
            R.layout.fragment_feed,
            container,
            false
        ).apply {
            this.viewModel = this@FeedFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        val fastAdapter = FastAdapter.with(modelAdapter).apply {
            this.onClickListener = object : ClickListener<PostItem> {
                override fun invoke(
                    v: View?,
                    adapter: IAdapter<PostItem>,
                    item: PostItem,
                    position: Int
                ): Boolean {
                    navigateToPostDetailsFragment(item.model.id)
                    return true
                }
            }

            object : ClickEventHook<PostItem>() {
                override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                    return if (viewHolder is PostItem.ViewHolder) {
                        viewHolder.binding.menuButton
                    } else {
                        null
                    }
                }

                override fun onClick(
                    v: View,
                    position: Int,
                    fastAdapter: FastAdapter<PostItem>,
                    item: PostItem
                ) {
                    popupMenu(v) {
                        if (item.model.isMine) {
                            inflate(R.menu.menu_my_post_item)
                        } else {
                            inflate(R.menu.menu_post_item)
                        }

                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.copyContentItem -> {
                                    copyPost(item.model)
                                    true
                                }

                                R.id.editPostItem -> {
                                    navigateToAddPostFragment(item.model.id)
                                    true
                                }

                                R.id.deletePostItem -> {
                                    dialog {
                                        title(R.string.delete_post_dialog_title)
                                        message(R.string.delete_post_dialog_message)
                                        positiveButton(R.string.yes) {
                                            viewModel.onDeleteButtonClick(item.model.id)
                                        }
                                        negativeButton(R.string.no) {
                                            this.dismiss()
                                        }
                                    }

                                    true
                                }

                                else -> {
                                    false
                                }
                            }
                        }
                    }
                }
            }.also { hook ->
                this.addEventHook(hook)
            }

            object : ClickEventHook<PostItem>() {
                override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                    return if (viewHolder is PostItem.ViewHolder) {
                        viewHolder.binding.shareButton
                    } else {
                        null
                    }
                }

                override fun onClick(
                    v: View,
                    position: Int,
                    fastAdapter: FastAdapter<PostItem>,
                    item: PostItem
                ) {
                    viewModel.onShareButtonClick(item.model.id)
                }
            }.also { hook ->
                this.addEventHook(hook)
            }

            object : ClickEventHook<PostItem>() {
                override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                    return if (viewHolder is PostItem.ViewHolder) {
                        viewHolder.binding.commentButton
                    } else {
                        null
                    }
                }

                override fun onClick(
                    v: View,
                    position: Int,
                    fastAdapter: FastAdapter<PostItem>,
                    item: PostItem
                ) {
                    navigateToPostDetailsFragment(item.model.id, true)
                }
            }.also { hook ->
                this.addEventHook(hook)
            }
        }

        binding.feedRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        binding.addPostButton.setOnClickListener {
            navigateToAddPostFragment()
        }
    }

    private fun observeViewModel() {
        viewModel.postsWithAuthorsResource.data.observe(viewLifecycleOwner) { postsWithAuthors ->
            modelAdapter.updateModels(postsWithAuthors)
        }

        viewModel.sharePostEvent.observe(viewLifecycleOwner) { event ->
            share(
                event.content,
                event.subject,
                event.chooserTitle
            )
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { messageResource ->
            view?.snackbar(messageResource)
        }
    }

    private fun copyPost(post: PostWithAuthor) {
        val isCopied = copyToClipboard("Post content", post.content)
        if (isCopied) {
            toast("Treść posta została skopiowana do schowka")
        } else {
            toast("Nie udało się skopiować treści posta")
        }
    }

    private fun navigateToPostDetailsFragment(postId: Long, showKeyboard: Boolean = false) {
        val direction = FeedFragmentDirections.actionFeedFragmentToPostDetailsFragment(postId, showKeyboard)
        navigateTo(direction)
    }

    private fun navigateToAddPostFragment(postId: Long = -1) {
        val direction = FeedFragmentDirections.actionFeedFragmentToAddPostFragment(postId)
        navigateTo(direction)
    }
}