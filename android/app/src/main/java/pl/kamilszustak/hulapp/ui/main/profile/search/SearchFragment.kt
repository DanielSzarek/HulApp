package pl.kamilszustak.hulapp.ui.main.profile.search

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.item.SearchPromptItem
import pl.kamilszustak.hulapp.data.item.UserSearchItem
import pl.kamilszustak.hulapp.data.model.SearchPrompt
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.databinding.FragmentSearchBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
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

        initializeSearchView()
        initializeModelAdapters()
        observeViewModel()
    }

    private fun initializeModelAdapters() {
        userModelAdapter = ModelAdapter { user ->
            UserSearchItem(user)
        }

        searchPromptModelAdapter = ModelAdapter { prompt ->
            SearchPromptItem(prompt)
        }
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

        userSearchView.setOnQueryTextListener(queryListener)
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
                    FastAdapter.with(userModelAdapter)
                }
                SearchViewModel.AdapterType.SEARCH_PROMPTS -> {
                    FastAdapter.with(searchPromptModelAdapter)
                }
            }
        }
    }
}