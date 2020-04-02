package pl.kamilszustak.hulapp.ui.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.LongClickListener
import com.mikepenz.fastadapter.adapters.ModelAdapter
import org.jetbrains.anko.support.v4.toast
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentFeedBinding
import pl.kamilszustak.hulapp.domain.item.PostItem
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.copyToClipboard
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.util.popupMenu
import pl.kamilszustak.hulapp.util.updateModels
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
            this.onLongClickListener = object : LongClickListener<PostItem> {
                override fun invoke(
                    v: View,
                    adapter: IAdapter<PostItem>,
                    item: PostItem,
                    position: Int
                ): Boolean {
                    popupMenu(v) {
                        this.inflate(R.menu.menu_post_item)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.copyContentItem -> {
                                    val isCopied = copyToClipboard("Post content", item.model.post.content)
                                    if (isCopied) {
                                        toast("Treść posta została skopiowana do schowka")
                                    } else {
                                        toast("Nie udało się skopiować treści posta")
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
    }

    private fun navigateToAddPostFragment() {
        val direction = FeedFragmentDirections.actionFeedFragmentToAddPostFragment()
        navigateTo(direction)
    }
}