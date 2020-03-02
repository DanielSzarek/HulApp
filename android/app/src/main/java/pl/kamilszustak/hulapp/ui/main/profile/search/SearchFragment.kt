package pl.kamilszustak.hulapp.ui.main.profile.search

import android.os.Bundle
import android.view.*
import android.widget.SearchView
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
import com.mikepenz.fastadapter.listeners.EventHook
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.item.SearchPromptItem
import pl.kamilszustak.hulapp.data.item.UserSearchItem
import pl.kamilszustak.hulapp.data.model.SearchPrompt
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.databinding.FragmentSearchBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.util.updateModels
import timber.log.Timber
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var userModelAdapter: ModelAdapter<User, UserSearchItem>
    private lateinit var searchPromptModelAdapter: ModelAdapter<SearchPrompt, SearchPromptItem>

    private lateinit var userFastAdapter: FastAdapter<UserSearchItem>
    private lateinit var searchPromptFastAdapter: FastAdapter<SearchPromptItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentSearchBinding>(
            inflater,
            R.layout.fragment_search,
            container,
            false
        ).apply {
            this.viewModel = this@SearchFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initializeSearchView()
        initializeAdapters()
        setListeners()
        observeViewModel()
    }

    private fun initializeAdapters() {
        initializeUserAdapter()
        initializeSearchPromptAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteSearchPromptsItem -> {
                viewModel.onDeleteSearchPromptsItemClick()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initializeUserAdapter() {
        userModelAdapter = ModelAdapter { user ->
            UserSearchItem(user)
        }
        userFastAdapter = FastAdapter.with(userModelAdapter)
        userFastAdapter.onClickListener = object : ClickListener<UserSearchItem> {
            override fun invoke(
                v: View?,
                adapter: IAdapter<UserSearchItem>,
                item: UserSearchItem,
                position: Int
            ): Boolean {
                navigateToUserProfileFragment(item.model.id)
                return true
            }
        }
    }

    private fun initializeSearchPromptAdapter() {
        searchPromptModelAdapter = ModelAdapter { prompt ->
            SearchPromptItem(prompt)
        }

        searchPromptFastAdapter = FastAdapter.with(searchPromptModelAdapter)
        searchPromptFastAdapter.onClickListener = { view, adapter, item, position ->
            searchView.setQuery(item.model.text, true)
            true
        }

        val eventHook = object : ClickEventHook<SearchPromptItem>() {
            override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                return if (viewHolder is SearchPromptItem.ViewHolder) {
                    viewHolder.deleteButton
                } else {
                    null
                }
            }
            override fun onClick(
                v: View,
                position: Int,
                fastAdapter: FastAdapter<SearchPromptItem>,
                item: SearchPromptItem
            ) {
                viewModel.onDeleteSearchPromptButtonClick(item.model.id)
            }
        }
        searchPromptFastAdapter.addEventHook(eventHook)
    }

    private fun initializeSearchView() {
        val queryListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onQueryTextChange(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query != null) {
                    viewModel.onQueryTextSubmit(query)
                    true
                } else {
                    false
                }
            }
        }

        searchView.setOnQueryTextListener(queryListener)
    }

    private fun setListeners() {
        searchButton.setOnClickListener {
            searchView.setQuery(searchView.query, true)
        }
    }

    private fun observeViewModel() {
        viewModel.usersResource.data.observe(viewLifecycleOwner) { users ->
            userModelAdapter.updateModels(users)
        }

        viewModel.usersResource.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressBar.show()
            } else {
                progressBar.hide()
            }
        }

        viewModel.usersResource.error.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }

        viewModel.searchPrompts.observe(viewLifecycleOwner) { prompts ->
            searchPromptModelAdapter.updateModels(prompts)
            Timber.i(prompts.toString())
        }

        viewModel.adapterType.observe(viewLifecycleOwner) { type ->
            recyclerView.adapter = when (type) {
                SearchViewModel.AdapterType.USERS -> {
                    userModelAdapter.clear()
                    userFastAdapter
                }
                SearchViewModel.AdapterType.SEARCH_PROMPTS -> {
                    searchPromptFastAdapter
                }
            }
        }
    }

    private fun navigateToUserProfileFragment(userId: Long) {
        val direction = SearchFragmentDirections.actionSearchFragmentToUserProfileFragment(userId)
        navigateTo(direction)
    }
}