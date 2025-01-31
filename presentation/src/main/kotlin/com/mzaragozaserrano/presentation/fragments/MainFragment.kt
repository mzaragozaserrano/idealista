package com.mzaragozaserrano.presentation.fragments

import android.os.Bundle
import android.view.View
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.base.BaseFragment
import com.mzaragozaserrano.presentation.composables.screens.MainScreen
import com.mzaragozaserrano.presentation.databinding.FragmentMainBinding
import com.mzaragozaserrano.presentation.theme.IdealistaAppTheme
import com.mzaragozaserrano.presentation.viewmodels.MainViewModel
import com.mzs.core.presentation.utils.viewBinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModel()
    override val binding by viewBinding(FragmentMainBinding::bind)

    override fun onBackPressed() {
        super.onBackPressed()
        TODO("Cerrar app")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.composeView.setContent {
            IdealistaAppTheme {
                MainScreen()
            }
        }
    }

}