package pl.kamilszustak.hulapp.ui.main.feed.post.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.LongClickListener
import com.mikepenz.fastadapter.adapters.ModelAdapter
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.toast
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentPostDetailsBinding
import pl.kamilszustak.hulapp.domain.item.CommentItem
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.*
import javax.inject.Inject

class PostDetailsFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: PostDetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentPostDetailsBinding
    private val args: PostDetailsFragmentArgs by navArgs()
    private val modelAdapter: ModelAdapter<CommentWithAuthor, CommentItem> by lazy {
        ModelAdapter<CommentWithAuthor, CommentItem> { commentWithAuthor ->
            CommentItem(commentWithAuthor)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentPostDetailsBinding>(
            inflater,
            R.layout.fragment_post_details,
            container,
            false
        ).apply {
            this.viewModel = this@PostDetailsFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setListeners()
        observeViewModel()
        viewModel.loadData(args.postId)

        if (args.showKeyboard) {
            binding.commentEditText.showKeyboard()
        }
    }

    private fun initializeRecyclerView() {
        val fastAdapter = FastAdapter.with(modelAdapter).apply {
            this.onLongClickListener = object : LongClickListener<CommentItem> {
                override fun invoke(
                    v: View,
                    adapter: IAdapter<CommentItem>,
                    item: CommentItem,
                    position: Int
                ): Boolean {
                    if (!item.model.isMine) {
                        return false
                    }

                    popupMenu(v) {
                        inflate(R.menu.menu_my_comment_item)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.editCommentItem -> {
                                    navigateToEditCommentFragment(item.model.id)
                                    true
                                }

                                R.id.deleteCommentItem -> {
                                    dialog {
                                        title(R.string.delete_comment_dialog_message)
                                        positiveButton(R.string.yes) {
                                            viewModel.onDeleteCommentButtonClick(item.model.id)
                                        }

                                        negativeButton(R.string.no) {
                                            it.dismiss()
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

                    return true
                }
            }
        }

        binding.commentsRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        binding.postLayout.shareButton.setOnClickListener {
            viewModel.onShareButtonClick(args.postId)
        }

        binding.addCommentButton.setOnClickListener {
            viewModel.onAddCommentButtonClick(args.postId)
        }

        binding.postLayout.commentButton.setOnClickListener {
            binding.commentEditText.showKeyboard()
        }

        binding.postLayout.menuButton.setOnClickListener {
            popupMenu(it) {
                if (viewModel.postWithAuthorResource.data.value?.isMine == true) {
                    inflate(R.menu.menu_my_post_item)
                } else {
                    inflate(R.menu.menu_post_item)
                }

                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.copyContentItem -> {
                            viewModel.postWithAuthorResource.data.value?.let { post ->
                                copyPost(post)
                            }
                            true
                        }

                        R.id.editPostItem -> {
                            navigateToAddPostFragment(args.postId)
                            true
                        }

                        R.id.deletePostItem -> {
                            dialog {
                                title(R.string.delete_post_dialog_title)
                                message(R.string.delete_post_dialog_message)
                                positiveButton(R.string.yes) {
                                    viewModel.onDeleteButtonClick(args.postId)                                }
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
    }

    private fun observeViewModel() {
        viewModel.sharePostEvent.observe(viewLifecycleOwner) { event ->
            share(
                event.content,
                event.subject,
                event.chooserTitle
            )
        }

        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            navigateUp()
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { messageResource ->
            view?.snackbar(messageResource)
        }

        viewModel.commentsWithAuthorsResource.data.observe(viewLifecycleOwner) { commentsWithAuthors ->
            modelAdapter.updateModels(commentsWithAuthors)
        }

        viewModel.commentAdded.observe(viewLifecycleOwner) {
            binding.scrollView.fullScroll(View.FOCUS_DOWN)
        }

        viewModel.hideKeyboard.observe(viewLifecycleOwner) {
            binding.commentEditText.hideKeyboard()
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

    private fun navigateToAddPostFragment(postId: Long = -1) {
        val direction = PostDetailsFragmentDirections.actionPostDetailsFragmentToAddPostFragment(postId)
        navigateTo(direction)
    }

    private fun navigateToEditCommentFragment(commentId: Long) {
        val direction = PostDetailsFragmentDirections.actionPostDetailsFragmentToEditCommentFragment(commentId)
        navigateTo(direction)
    }
}