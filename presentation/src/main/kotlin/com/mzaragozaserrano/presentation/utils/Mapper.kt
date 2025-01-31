package com.mzaragozaserrano.presentation.utils

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.FeaturesBO
import com.mzaragozaserrano.domain.bo.PriceBO
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.Feature
import com.mzs.core.presentation.utils.generic.emptyText
import java.text.DecimalFormat
import java.text.ParseException


fun List<AdBO>.transform(): List<AdVO> = map { it.transform() }

fun AdBO.transform(): AdVO = AdVO(
    features = features.transform(),
    price = priceInfo?.price.transform(),
    subtitle = createSubtitle(
        province = province,
        district = district,
        municipality = municipality
    ),
    thumbnail = thumbnail.orEmpty(),
    title = createTitle(address = address, propertyType = propertyType)
)

fun FeaturesBO?.transform(): List<Feature> {
    val features = mutableListOf<Feature>()
    if(this != null) {
        if (hasAirConditioning) features.add(Feature.AirConditioning)
        if (hasBoxRoom) features.add(Feature.BoxRoom)
        if (hasGarden) features.add(Feature.Garden)
        if (hasSwimmingPool) features.add(Feature.SwimmingPool)
        if (hasTerrace) features.add(Feature.Terrace)
    }
    return features
}

fun PriceBO?.transform(): String {
    val format = DecimalFormat("###,###.##")
    format.applyPattern("#,###.##")
    return if (this != null) {
        try {
            format.format(amount) + currencySuffix
        } catch (e: ParseException) {
            emptyText
        }
    } else {
        emptyText
    }
}

private fun createSubtitle(
    province: String?,
    district: String?,
    municipality: String?,
): String {
    val subtitle = StringBuilder()
    when {
        province != null && district != null && municipality != null -> {
            subtitle.append("$province - $district, $municipality")
        }

        province != null && district != null -> {
            subtitle.append("$province - $district")
        }

        province != null && municipality != null -> {
            subtitle.append("$province, $municipality")
        }

        district != null && municipality != null -> {
            subtitle.append("$district, $municipality")
        }

        province != null -> {
            subtitle.append(province)
        }

        district != null -> {
            subtitle.append(district)
        }

        municipality != null -> {
            subtitle.append(municipality)
        }

        else -> {
            subtitle.append(emptyText)
        }
    }
    return subtitle.toString()
}

private fun createTitle(address: String?, propertyType: String?): String {
    val title = StringBuilder()
    when {
        address != null && propertyType != null -> {
            title.append("$propertyType $address")
        }

        address != null -> {
            title.append(address)
        }

        else -> {
            title.append(emptyText)
        }
    }
    return title.toString()
}

