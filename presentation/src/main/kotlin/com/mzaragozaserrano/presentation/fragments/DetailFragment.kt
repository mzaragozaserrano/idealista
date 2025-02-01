package com.mzaragozaserrano.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.base.BaseFragment
import com.mzaragozaserrano.presentation.databinding.FragmentDetailBinding
import com.mzaragozaserrano.presentation.viewmodels.DetailViewModel
import com.mzs.core.presentation.utils.viewBinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment :
    BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModel()
    override val binding by viewBinding(FragmentDetailBinding::bind)

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}