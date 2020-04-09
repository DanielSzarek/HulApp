package pl.kamilszustak.hulapp.ui.main.feed.post.add

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentAddPostBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateUp
import javax.inject.Inject

class AddPostFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: AddPostViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentAddPostBinding
    private val args: AddPostFragmentArgs by navArgs()

    private val inEditMode: Boolean = (args.postId != -1L)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAddPostBinding>(
            inflater,
            R.layout.fragment_add_post,
            container,
            false
        ).apply {
            this.viewModel = this@AddPostFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_post_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addPostItem -> {
                viewModel.onAddPostButtonClick()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.completed.observe(viewLifecycleOwner) {
            navigateUp()
        }

        viewModel.error.observe(viewLifecycleOwner) { messageResource ->
            view?.snackbar(messageResource)
        }
    }
}