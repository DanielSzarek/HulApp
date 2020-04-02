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
import com.mikepenz.fastadapter.adapters.ModelAdapter
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentFeedBinding
import pl.kamilszustak.hulapp.domain.item.PostItem
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.ui.base.BaseFragment
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
        val fastAdapter = FastAdapter.with(modelAdapter)

        binding.feedRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun observeViewModel() {
        viewModel.postsWithAuthorsResource.data.observe(viewLifecycleOwner) { postsWithAuthors ->
            modelAdapter.updateModels(postsWithAuthors)
        }
    }
}