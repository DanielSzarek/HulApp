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
import pl.kamilszustak.hulapp.data.item.UserSearchItem
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.databinding.FragmentSearchBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.updateModels
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var modelAdapter: ModelAdapter<User, UserSearchItem>

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.searchItem)
        val searchView = searchItem.actionView as? SearchView

        val queryListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query != null) {
                    viewModel.onSubmit(query)
                    true
                } else {
                    false
                }
            }
        }

        searchView?.apply {
            this.isIconifiedByDefault = false
            this.setOnQueryTextListener(queryListener)
            this.requestFocus()
            this.queryHint = "Szukaj"
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initializeRecyclerView()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        modelAdapter = ModelAdapter { user ->
            UserSearchItem(user)
        }

        val fastAdapter = FastAdapter.with(modelAdapter)
        usersRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.usersResource.data.observe(viewLifecycleOwner) { users ->
            modelAdapter.updateModels(users)
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
    }
}