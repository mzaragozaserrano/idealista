package com.mzaragozaserrano.presentation.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
import com.mzaragozaserrano.presentation.vo.MoreInfo
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(ad = getSerializableArgument(BUNDLE_KEY, AdVO::class.java))
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
                            height = 24.dp,
                            skeletonable = titleId,
                            textColor = MaterialTheme.colorScheme.onBackground,
                            textStyle = MaterialTheme.typography.titleLarge,
                            width = 128.dp
                        )
                        TextSkeleton(
                            height = 16.dp,
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
        if (moreInfo?.isNotEmpty() == true) {
            checkCardsVisibility(moreInfo = moreInfo)
            moreInfo.forEach { info ->
                when (info) {
                    is MoreInfo.Building.Floor -> {
                        binding.buildingCard.contentInfo.addView(
                            adInfo(text = getString(info.textId, info.floor, info.exterior))
                        )
                    }

                    is MoreInfo.Building.Lift -> {
                        binding.buildingCard.contentInfo.addView(adInfo(text = info.value))
                    }

                    is MoreInfo.EnergyCertification.Consume -> {
                        binding.energyCertificationCard.contentInfo.addView(
                            adInfo(
                                text = getString(
                                    info.textId,
                                    info.value
                                )
                            )
                        )
                    }

                    is MoreInfo.EnergyCertification.Emission -> {
                        binding.energyCertificationCard.contentInfo.addView(
                            adInfo(
                                text = getString(
                                    info.textId,
                                    info.value
                                )
                            )
                        )
                    }

                    is MoreInfo.Generic.Bathroom -> {
                        binding.genericCard.contentInfo.addView(
                            adInfo(
                                text = resources.getQuantityString(
                                    info.textId,
                                    info.value,
                                    info.value
                                )
                            )
                        )
                    }
                    is MoreInfo.Generic.ConstructedArea -> {
                        binding.genericCard.contentInfo.addView(
                            adInfo(
                                text = getString(
                                    info.textId,
                                    info.value
                                )
                            )
                        )
                    }
                    is MoreInfo.Generic.Rooms -> {
                        binding.genericCard.contentInfo.addView(
                            adInfo(
                                text = resources.getQuantityString(
                                    info.textId,
                                    info.value,
                                    info.value
                                )
                            )
                        )
                    }
                    is MoreInfo.Generic.Status -> {
                        binding.genericCard.contentInfo.addView(adInfo(text = info.value))
                    }
                }
            }
        }
    }

    private fun checkCardsVisibility(moreInfo: List<MoreInfo>) {
        if (moreInfo.any { it is MoreInfo.Building }) {
            val info = moreInfo.first { it is MoreInfo.Building } as MoreInfo.Building
            binding.buildingCard.apply {
                content.visibility = View.VISIBLE
                infoIcon.setImageResource(info.iconId)
                infoTitle.text = getString(info.labelId)
            }
        }
        if (moreInfo.any { it is MoreInfo.EnergyCertification }) {
            val info =
                moreInfo.first { it is MoreInfo.EnergyCertification } as MoreInfo.EnergyCertification
            binding.energyCertificationCard.apply {
                content.visibility = View.VISIBLE
                infoIcon.setImageResource(info.iconId)
                infoTitle.text = getString(info.labelId)
            }
        }
        if (moreInfo.any { it is MoreInfo.Generic }) {
            val info =
                moreInfo.first { it is MoreInfo.Generic } as MoreInfo.Generic
            binding.genericCard.apply {
                content.visibility = View.VISIBLE
                infoIcon.setImageResource(info.iconId)
                infoTitle.text = getString(info.labelId)
            }
        }
    }

    private fun adInfo(text: String): AppCompatTextView {
        val textView = AppCompatTextView(requireContext()).apply {
            ellipsize = TextUtils.TruncateAt.END
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            maxLines = 1
            setTextColor(ContextCompat.getColor(context, R.color.on_tertiary_container))
            this.text = text
            textSize = 16f
        }
        return textView
    }

}