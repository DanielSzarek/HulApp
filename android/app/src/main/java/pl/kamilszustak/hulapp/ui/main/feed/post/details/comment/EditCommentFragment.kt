package pl.kamilszustak.hulapp.ui.main.feed.post.details.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentEditCommentBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import javax.inject.Inject

class EditCommentFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: EditCommentViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentEditCommentBinding
    private val args: EditCommentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentEditCommentBinding>(
            inflater,
            R.layout.fragment_edit_comment,
            container,
            false
        ).apply {
            this.viewModel = this@EditCommentFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}