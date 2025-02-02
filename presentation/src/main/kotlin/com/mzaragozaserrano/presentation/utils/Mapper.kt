package com.mzaragozaserrano.presentation.utils

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.DetailedAdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.bo.FeaturesBO
import com.mzaragozaserrano.domain.bo.ParkingSpaceBO
import com.mzaragozaserrano.domain.bo.PriceBO
import com.mzaragozaserrano.domain.bo.PriceInfoBO
import com.mzaragozaserrano.domain.utils.DomainStringResource
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.vo.AdType
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.DetailedAdVO
import com.mzaragozaserrano.presentation.vo.ErrorVO
import com.mzaragozaserrano.presentation.vo.Feature
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzaragozaserrano.presentation.vo.Info
import com.mzs.core.domain.utils.generic.DateUtils
import com.mzs.core.domain.utils.generic.ddMMyyyy
import com.mzs.core.presentation.components.compose.utils.toSkeletonable
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

fun AdBO.transform(isFavorite: Boolean? = null): AdVO {
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
        currencySuffix = priceInfo?.price?.currencySuffix.orEmpty(),
        date = date,
        extraInfo = extraInfo,
        hasNotInformation = extraInfo.isEmpty() && features.isEmpty() && price.isEmpty() && subtitle.isEmpty() && title.isEmpty(),
        id = propertyCode.orEmpty(),
        isFavorite = isFavorite ?: this.isFavorite,
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

private fun String?.transformToStringResource(): DomainStringResource? {
    return when (this) {
        "flat" -> DomainStringResource.Flat
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
            format.format(amount)
        } catch (e: ParseException) {
            emptyText
        }
    } else {
        emptyText
    }
}

fun String.transform(currencySuffix: String?): PriceInfoBO? {
    val format = DecimalFormat("#,###.##")
    val parsedPrice = try {
        format.parse(this)?.toDouble()
    } catch (e: ParseException) {
        null
    }
    return parsedPrice?.let {
        PriceInfoBO(
            price = PriceBO(
                amount = it,
                currencySuffix = currencySuffix
            )
        )
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

        province != null -> {
            subtitle.append(province)
        }

        else -> {
            subtitle.append(emptyText)
        }
    }
    return subtitle.toString()
}

private fun String.getLocation(): Triple<String?, String?, String?> {
    return when {
        contains(" - ") && contains(",") -> {
            val parts = split(" - ", ",")
            Triple(parts[0], parts[1], parts[2])
        }

        contains(" - ") -> {
            val parts = split(" - ")
            Triple(parts[0], parts[1], null)
        }

        contains(",") -> {
            val parts = split(",")
            Triple(null, parts[0], parts[1])
        }

        else -> {
            Triple(this, null, null)
        }
    }
}

fun AdVO.transform(): AdBO {
    val exterior = extraInfo.filterIsInstance<Info.Floor>().firstOrNull()?.secondValue
    val floor = extraInfo.filterIsInstance<Info.Floor>().firstOrNull()?.value
    val rooms = extraInfo.filterIsInstance<Info.Rooms>().firstOrNull()?.value?.toIntOrNull()
    val size =
        extraInfo.filterIsInstance<Info.SquareMeters>().firstOrNull()?.value?.toDoubleOrNull()
    val (province, district, municipality) = subtitle.getLocation()
    return AdBO(
        address = title.lowercase(),
        date = date,
        district = district,
        exterior = exterior,
        features = FeaturesBO(
            hasAirConditioning = features.contains(Feature.AirConditioning),
            hasBoxRoom = features.contains(Feature.BoxRoom),
            hasGarden = features.contains(Feature.Garden),
            hasSwimmingPool = features.contains(Feature.SwimmingPool),
            hasTerrace = features.contains(Feature.Terrace)
        ),
        floor = floor,
        isFavorite = isFavorite,
        municipality = municipality,
        operation = type.transform(),
        parkingSpace = ParkingSpaceBO(hasParkingSpace = features.contains(Feature.Parking)),
        priceInfo = price.transform(currencySuffix = currencySuffix),
        propertyCode = id,
        propertyType = prefixTitle.transform(),
        province = province,
        rooms = rooms,
        size = size,
        thumbnail = thumbnail
    )
}

private fun DomainStringResource?.transform(): String? {
    return if (this is DomainStringResource.Flat) "flat" else null
}


private fun AdType?.transform(): String? {
    return when (this) {
        AdType.Sale -> "sale"
        AdType.Rent -> "rent"
        else -> null
    }
}

fun List<AdVO>.transform(dateUtils: DateUtils) = groupBy { ad ->
    dateUtils.formatDateToFriendlyString(
        dateUtils.convertStringToLocalDate(
            dateString = ad.date?.split(" ")?.get(0).orEmpty(),
            format = ddMMyyyy
        )
    )
}.toList().reversed().toMap()

fun DetailedAdBO.transform(): DetailedAdVO = DetailedAdVO(
    description = propertyComment.orEmpty().toSkeletonable(),
    latitude = ubication?.latitude,
    longitude = ubication?.longitude,
//    moreCharacteristics = moreCharacteristics,
    multimedia = multimedia?.images?.map { it.url.orEmpty() }.orEmpty(),
    tags = listOf(),
    titleId = PresentationStringResource.TitleDescription.textId.toSkeletonable()
)

fun Int?.transform(): Filter {
    return when (this) {
        R.string.type_rent -> {
            Filter.Rent()
        }

        R.string.type_sale -> {
            Filter.Sale()
        }

        else -> {
            Filter.All
        }
    }
}