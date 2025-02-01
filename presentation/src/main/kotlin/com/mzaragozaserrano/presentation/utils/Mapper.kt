package com.mzaragozaserrano.presentation.utils

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.bo.FavoriteAdBO
import com.mzaragozaserrano.domain.bo.FeaturesBO
import com.mzaragozaserrano.domain.bo.PriceBO
import com.mzaragozaserrano.domain.bo.StringResource
import com.mzaragozaserrano.presentation.vo.AdType
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.ErrorVO
import com.mzaragozaserrano.presentation.vo.FavoriteAdVO
import com.mzaragozaserrano.presentation.vo.Feature
import com.mzaragozaserrano.presentation.vo.Info
import com.mzs.core.domain.utils.generic.DateUtils
import com.mzs.core.domain.utils.generic.ddMMyyyy
import com.mzs.core.presentation.utils.extensions.capitalize
import com.mzs.core.presentation.utils.generic.emptyText
import java.text.DecimalFormat
import java.text.ParseException

fun ErrorBO.transform(): ErrorVO = when (this) {
    is ErrorBO.Connectivity -> ErrorVO.Connectivity
    is ErrorBO.DataNotFound -> ErrorVO.DataNotFound
    is ErrorBO.DeserializingJSON -> ErrorVO.DeserializingJSON
    is ErrorBO.Generic -> ErrorVO.Generic(id = id)
    is ErrorBO.LoadingData -> ErrorVO.LoadingData
    is ErrorBO.LoadingURL -> ErrorVO.LoadingURL
}

fun List<AdBO>.transform(): List<AdVO> = map { it.transform() }

fun AdBO.transform(): AdVO {
    val extraInfo =
        createExtraInfoList(exterior = exterior, floor = floor, rooms = rooms, size = size)
    val features = features.transform(hasParking = parkingSpace?.hasParkingSpace ?: false)
    val price = priceInfo?.price.transform()
    val subtitle = createSubtitle(
        province = province,
        district = district,
        municipality = municipality
    )
    val title = address.orEmpty().capitalize()
    return AdVO(
        extraInfo = extraInfo,
        hasNotInformation = extraInfo.isEmpty() && features.isEmpty() && price.isEmpty() && subtitle.isEmpty() && title.isEmpty(),
        id = propertyCode.orEmpty(),
        isFavorite = isFavorite,
        features = features,
        prefixTitle = propertyType.transformToStringResource(),
        price = price,
        subtitle = subtitle,
        thumbnail = thumbnail.orEmpty(),
        title = title,
        type = operation.transformToAdType()
    )
}

private fun createExtraInfoList(
    exterior: String?,
    floor: String?,
    rooms: Int?,
    size: Double?,
): List<Info> {
    val list = mutableListOf<Info>()
    when {
        floor != null && exterior != null -> {
            list.add(element = Info.Floor(value = floor, secondValue = exterior))
        }

        floor != null -> {
            list.add(element = Info.Floor(value = floor))
        }
    }
    if (rooms != null) list.add(element = Info.Rooms(value = rooms.toString()))
    if (size != null) list.add(element = Info.SquareMeters(value = size.toString()))
    return list
}


fun FeaturesBO?.transform(hasParking: Boolean): List<Feature> {
    val features = mutableListOf<Feature>()
    if (this != null) {
        if (hasAirConditioning) features.add(element = Feature.AirConditioning)
        if (hasBoxRoom) features.add(element = Feature.BoxRoom)
        if (hasGarden) features.add(element = Feature.Garden)
        if (hasParking) features.add(element = Feature.Parking)
        if (hasSwimmingPool) features.add(element = Feature.SwimmingPool)
        if (hasTerrace) features.add(element = Feature.Terrace)
    }
    return features
}

private fun String?.transformToStringResource(): Int? {
    return when (this) {
        "flat" -> StringResource.Flat.textId
        else -> null
    }
}

private fun String?.transformToAdType(): AdType? {
    return when (this) {
        "sale" -> AdType.Sale
        "rent" -> AdType.Rent
        else -> null
    }
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

fun AdVO.transform(): FavoriteAdBO =
    FavoriteAdBO(
        id = id,
        operation = type.transform(),
        price = price,
        subtitle = subtitle,
        thumbnail = thumbnail,
        title = title
    )

private fun AdType?.transform(): String? {
    return when (this) {
        AdType.Sale -> "sale"
        AdType.Rent -> "rent"
        else -> null
    }
}

fun FavoriteAdBO.transform(): FavoriteAdVO = FavoriteAdVO(
    id = id.orEmpty(),
    date = date.orEmpty(),
    price = price.orEmpty(),
    subtitle = subtitle.orEmpty(),
    title = title.orEmpty(),
    thumbnail = thumbnail.orEmpty(),
    type = operation.transformToAdType()
)

fun List<FavoriteAdVO>.transform(dateUtils: DateUtils) = groupBy { ad ->
    dateUtils.formatDateToFriendlyString(
        dateUtils.convertStringToLocalDate(
            dateString = ad.date?.split(" ")?.get(1).orEmpty(),
            format = ddMMyyyy
        )
    )
}