package com.mzaragozaserrano.presentation.fragments

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    override fun MainViewModel.setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect(::handleNavigation)
            }
        }
    }

    private fun handleNavigation(navigationEvent: BaseComposeViewModel.NavigationEvent) {
        when (navigationEvent) {
            is MainViewModel.NavigationType.Detail -> {
                findNavController().navigate(R.id.action_MainFragment_to_DetailFragment)
            }
        }
    }

    private fun setUpView() {
        binding.composeView.setContent {
            IdealistaAppTheme {
                MainScreen {
                    viewModel.onGoToDetail()
                }
            }
        }
    }

}