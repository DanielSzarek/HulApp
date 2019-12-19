package pl.kamilszustak.hulapp.ui.view.main.profile

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentEditProfileBinding
import pl.kamilszustak.hulapp.ui.view.base.BaseFragment
import pl.kamilszustak.hulapp.ui.viewmodel.main.profile.EditProfileViewModel
import javax.inject.Inject

class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: EditProfileViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentEditProfileBinding>(
            inflater,
            R.layout.fragment_edit_profile,
            container,
            false
        ).apply {
            this.viewModel = this@EditProfileFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_profile_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.saveButton -> {
                viewModel.onSaveButtonClick()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}