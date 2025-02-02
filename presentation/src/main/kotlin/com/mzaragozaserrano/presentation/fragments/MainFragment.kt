package com.mzaragozaserrano.presentation.fragments

import androidx.core.bundle.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.base.BaseComposeViewModel
import com.mzaragozaserrano.presentation.base.BaseFragment
import com.mzaragozaserrano.presentation.composables.screens.MainScreen
import com.mzaragozaserrano.presentation.databinding.FragmentMainBinding
import com.mzaragozaserrano.presentation.theme.IdealistaAppTheme
import com.mzaragozaserrano.presentation.viewmodels.MainViewModel
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzs.core.presentation.utils.extensions.getSerializableBundle
import com.mzs.core.presentation.utils.viewBinding.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModel()
    override val binding by viewBinding(FragmentMainBinding::bind)

    override fun onBackPressed() {
        super.onBackPressed()
        requireActivity().finish()
    }

    override fun MainViewModel.setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect(::handleNavigation)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleUiState)
            }
        }
        parentFragmentManager.setFragmentResultListener(
            DetailFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            viewModel.onSetForceToRefresh(
                adChanged = bundle.getSerializableBundle(
                    DetailFragment.AD_CHANGED,
                    AdVO::class.java
                )
            )
        }
    }

    private fun handleNavigation(navigationEvent: BaseComposeViewModel.NavigationEvent) {
        when (navigationEvent) {
            is MainViewModel.NavigationType.Detail -> {
                viewModel.onSetForceToRefresh(adChanged = null)
                findNavController().navigate(
                    R.id.action_MainFragment_to_DetailFragment,
                    bundleOf(DetailFragment.BUNDLE_KEY to navigationEvent.ad)
                )
            }
        }
    }

    private fun handleUiState(uiState: BaseComposeViewModel.UiState<MainViewModel.MainVO>) {
        uiState.success?.let { success ->
            binding.composeView.setContent {
                IdealistaAppTheme {
                    MainScreen(adChanged = success.adChanged) { ad ->
                        viewModel.onGoToDetail(ad = ad)
                    }
                }
            }
        }
    }

}