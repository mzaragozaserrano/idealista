package com.mzaragozaserrano.presentation.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.mzaragozaserrano.presentation.utils.hideError
import com.mzaragozaserrano.presentation.utils.showError
import com.mzaragozaserrano.presentation.viewmodels.DetailViewModel
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.DetailedAdVO
import com.mzaragozaserrano.presentation.vo.MoreInfo
import com.mzs.core.presentation.components.compose.images.LottieImage
import com.mzs.core.presentation.components.compose.utils.hasData
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect(::handleNavigation)
            }
        }
    }

    private fun handleNavigation(navigationEvent: BaseComposeViewModel.NavigationEvent) {
        when (navigationEvent) {
            is DetailViewModel.NavigationType.GoogleMaps -> {
                startActivity(navigationEvent.intent)
            }
        }
    }

    private fun handleUiState(uiState: BaseComposeViewModel.UiState<DetailViewModel.DetailVO>) {
        when {
            uiState.error != null -> {
                uiState.success?.ad?.let { adAux -> setUpInitialView(ad = adAux) }
                showError(
                    buttonText = getString(R.string.button_retry),
                    message = getString(uiState.error.messageId),
                    title = getString(R.string.title_error),
                    onButtonClicked = {
                        viewModel.onExecuteGetDetailedAd()
                    }
                )
            }

            uiState.success != null -> {
                hideError()
                setUpDetailedView(uiState.success.detailedAd, uiState.success.isMoreInfoLoaded)
                setUpInitialView(uiState.success.ad)
            }
        }
    }

    private fun setUpDetailedView(detailedAd: DetailedAdVO, isMoreInfoLoaded: Boolean) {
        with(detailedAd) {
            setUpDescription()
            setUpImagePager()
            if (isMoreInfoLoaded.not()) {
                setUpMoreInfo()
            }
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
                    isMapButtonEnabled = latitude != null && longitude != null,
                    onFavoriteButtonClicked = { isFavorite -> viewModel.onSetFavorite(isFavorite = isFavorite) },
                    onMapButtonClicked = {
                        viewModel.onGoToGoogleMaps(latitude = latitude, longitude = longitude)
                    }
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
                var expanded by remember { mutableStateOf(value = false) }
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
                            maxLines = if (expanded) Int.MAX_VALUE else 5,
                            textColor = MaterialTheme.colorScheme.onBackground,
                            textStyle = MaterialTheme.typography.titleMedium
                        )
                        if (description.hasData()) {
                            Text(
                                modifier = Modifier.clickable { expanded = expanded.not() },
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelLarge,
                                text = if (expanded) {
                                    stringResource(id = R.string.see_less)
                                } else {
                                    stringResource(id = R.string.see_more)
                                }
                            )
                        }
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

    private fun DetailedAdVO.setUpMoreInfo() {
        if (moreInfo?.isNotEmpty() == true) {
            checkCardsVisibility(moreInfo = moreInfo)
            moreInfo.forEach { info ->
                when (info) {
                    is MoreInfo.Building.Floor -> {
                        binding.buildingCard.contentInfo.addView(
                            addInfo(text = getString(info.textId, info.floor, info.exterior))
                        )
                    }

                    is MoreInfo.Building.Lift -> {
                        binding.buildingCard.contentInfo.addView(addInfo(text = info.value))
                    }

                    is MoreInfo.EnergyCertification.Consume -> {
                        binding.energyCertificationCard.contentInfo.addView(
                            addInfo(
                                text = getString(
                                    info.textId,
                                    info.value
                                )
                            )
                        )
                    }

                    is MoreInfo.EnergyCertification.Emission -> {
                        binding.energyCertificationCard.contentInfo.addView(
                            addInfo(
                                text = getString(
                                    info.textId,
                                    info.value
                                )
                            )
                        )
                    }

                    is MoreInfo.Generic.Bathroom -> {
                        binding.genericCard.contentInfo.addView(
                            addInfo(
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
                            addInfo(
                                text = getString(
                                    info.textId,
                                    info.value
                                )
                            )
                        )
                    }

                    is MoreInfo.Generic.Rooms -> {
                        binding.genericCard.contentInfo.addView(
                            addInfo(
                                text = resources.getQuantityString(
                                    info.textId,
                                    info.value,
                                    info.value
                                )
                            )
                        )
                    }

                    is MoreInfo.Generic.Status -> {
                        binding.genericCard.contentInfo.addView(addInfo(text = info.value))
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

    private fun addInfo(text: String): AppCompatTextView {
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