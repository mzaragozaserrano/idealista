package com.mzaragozaserrano.presentation.fragments

import android.os.Bundle
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.base.BaseFragment
import com.mzaragozaserrano.presentation.base.BaseViewModel
import com.mzaragozaserrano.presentation.composables.HomeScreen
import com.mzaragozaserrano.presentation.databinding.FragmentHomeBinding
import com.mzaragozaserrano.presentation.theme.IdealistaAppTheme
import com.mzaragozaserrano.presentation.viewmodels.HomeViewModel
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzs.core.presentation.utils.viewBinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModel()
    override val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onBackPressed() {
        super.onBackPressed()
        TODO("Cerrar app")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun FragmentHomeBinding.setUpListeners() {

    }

    override fun HomeViewModel.setUpObservers() {
        uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BaseViewModel.UiState.Error -> {

                }

                is BaseViewModel.UiState.Loading -> {

                }

                is BaseViewModel.UiState.Success -> {
                    setUpView(state.data)
                }
            }
        }
    }

    private fun setUpView(data: List<AdVO>) {
        binding.composeView.setContent {
            IdealistaAppTheme {
                HomeScreen(data = data) { }
            }
        }
    }

}