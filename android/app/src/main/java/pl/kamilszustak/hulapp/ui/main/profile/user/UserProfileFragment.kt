package pl.kamilszustak.hulapp.ui.main.profile.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentUserProfileBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import javax.inject.Inject

class UserProfileFragment : BaseFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: UserProfileViewModel by viewModels {
        viewModelFactory
    }

    private val args: UserProfileFragmentArgs by navArgs()

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentUserProfileBinding>(
            inflater,
            R.layout.fragment_user_profile,
            container,
            false
        ).apply {
            this.viewModel = this@UserProfileFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        viewModel.loadData(args.userId)
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadData(args.userId, true)
        }
    }
}