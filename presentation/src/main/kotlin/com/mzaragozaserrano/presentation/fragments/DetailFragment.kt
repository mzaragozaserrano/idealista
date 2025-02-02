package com.mzaragozaserrano.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.base.BaseComposeViewModel
import com.mzaragozaserrano.presentation.base.BaseFragment
import com.mzaragozaserrano.presentation.composables.items.DetailButtons
import com.mzaragozaserrano.presentation.composables.items.MultimediaCarousel
import com.mzaragozaserrano.presentation.composables.items.TextSkeleton
import com.mzaragozaserrano.presentation.databinding.FragmentDetailBinding
import com.mzaragozaserrano.presentation.theme.IdealistaAppTheme
import com.mzaragozaserrano.presentation.viewmodels.DetailViewModel
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.DetailedAdVO
import com.mzs.core.presentation.components.compose.images.LottieImage
import com.mzs.core.presentation.utils.extensions.getSerializableArgument
import com.mzs.core.presentation.utils.viewBinding.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment :
    BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {

    companion object {
        const val BUNDLE_KEY = "DetailFragment.BUNDLE_KEY"
        const val AD_CHANGED = "DetailFragment.IS_CHANGED"
        const val REQUEST_KEY = "DetailFragment.REQUEST_KEY"
    }

    override val viewModel: DetailViewModel by viewModel()
    override val binding by viewBinding(FragmentDetailBinding::bind)

    override fun onBackPressed() {
        super.onBackPressed()
        val isChanged = viewModel.onCheckChanged()
        parentFragmentManager.setFragmentResult(REQUEST_KEY, isChanged)
        findNavController().popBackStack()
    }

    override fun FragmentDetailBinding.setUpListeners() {
        binding.closeButton.apply {
            setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun DetailViewModel.setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleUiState)
            }
        }
    }

    private fun handleUiState(uiState: BaseComposeViewModel.UiState<DetailViewModel.DetailVO>) {
        when {
            uiState.error != null -> {

            }
            uiState.success != null -> {
                setUpDetailedView(uiState.success.detailedAd)
                setUpInitialView(uiState.success.ad)
            }
        }
    }

    private fun setUpDetailedView(detailedAd: DetailedAdVO) {
        with(detailedAd) {
            setUpDescription()
            setUpImagePager()
            setUpTags()
        }
    }

    private fun setUpInitialView(ad: AdVO) {
        with(ad) {
            setUpButtons()
            setUpPrice()
            setUpSubtitle()
            setUpTitle()
        }
    }

    private fun AdVO.setUpButtons() {
        binding.composeButtons.setContent {
            IdealistaAppTheme {
                DetailButtons(
                    isFavorite = isFavorite,
                    onFavoriteButtonClicked = { isFavorite -> viewModel.onSetFavorite(isFavorite = isFavorite) },
                    onMapButtonClicked = {}
                )
            }
        }
    }

    private fun AdVO.setUpPrice() {
        if (price.isNotEmpty()) {
            binding.textViewPrice.text = String.format("%s%s", price, currencySuffix)
        } else {
            binding.textViewPrice.visibility = View.INVISIBLE
        }
    }

    private fun AdVO.setUpSubtitle() {
        if (subtitle.isNotEmpty()) {
            binding.textViewSubtitle.text = subtitle
//            binding.textViewSubtitle.layout.getEllipsisCount(binding.textViewSubtitle.layout.lineCount - 1) > 0
        } else {
            binding.textViewSubtitle.visibility = View.INVISIBLE
        }
    }


    private fun AdVO.setUpTitle() {
        val prefixTitleId = prefixTitle?.textId
        if (prefixTitleId != null) {
            binding.textViewTitle.text =
                getString(prefixTitleId, title)
        } else {
            binding.textViewTitle.visibility = View.INVISIBLE
        }
    }

    private fun DetailedAdVO.setUpDescription() {
        binding.composeDescription.setContent {
            IdealistaAppTheme {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 4.dp),
                    content = {
                        TextSkeleton(
                            skeletonable = titleId,
                            textColor = MaterialTheme.colorScheme.onBackground,
                            textStyle = MaterialTheme.typography.titleLarge,
                            width = 128.dp
                        )
                        TextSkeleton(
                            skeletonable = description,
                            maxLines = 5,
                            textColor = MaterialTheme.colorScheme.onBackground,
                            textStyle = MaterialTheme.typography.titleMedium
                        )
                    }
                )
            }
        }
    }

    private fun DetailedAdVO.setUpImagePager() {
        binding.composeMultimedia.setContent {
            IdealistaAppTheme {
                if (multimedia.isNullOrEmpty()) {
                    LottieImage(
                        modifier = Modifier.padding(all = 64.dp),
                        animationId = R.raw.image_loading
                    )
                } else {
                    MultimediaCarousel(images = multimedia)
                }
            }
        }
    }

    private fun DetailedAdVO.setUpTags() {
        binding.composeTags.setContent {
            IdealistaAppTheme {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onCreate(ad = getSerializableArgument(BUNDLE_KEY, AdVO::class.java))
    }

}