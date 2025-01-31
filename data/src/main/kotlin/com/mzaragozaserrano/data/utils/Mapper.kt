package com.mzaragozaserrano.data.utils

import com.mzaragozaserrano.data.dto.AdDTO
import com.mzaragozaserrano.data.dto.AdDetailDTO
import com.mzaragozaserrano.data.dto.EnergyCertificationDTO
import com.mzaragozaserrano.data.dto.EnergyTypeDTO
import com.mzaragozaserrano.data.dto.ErrorDTO
import com.mzaragozaserrano.data.dto.FeaturesDTO
import com.mzaragozaserrano.data.dto.ImageDTO
import com.mzaragozaserrano.data.dto.ImageDetailDTO
import com.mzaragozaserrano.data.dto.MoreCharacteristicsDTO
import com.mzaragozaserrano.data.dto.MultimediaDTO
import com.mzaragozaserrano.data.dto.MultimediaDetailDTO
import com.mzaragozaserrano.data.dto.ParkingSpaceDTO
import com.mzaragozaserrano.data.dto.PriceDTO
import com.mzaragozaserrano.data.dto.PriceInfoDTO
import com.mzaragozaserrano.data.dto.UbicationDTO
import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.AdDetailBO
import com.mzaragozaserrano.domain.bo.EnergyCertificationBO
import com.mzaragozaserrano.domain.bo.EnergyTypeBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.bo.FeaturesBO
import com.mzaragozaserrano.domain.bo.ImageBO
import com.mzaragozaserrano.domain.bo.ImageDetailBO
import com.mzaragozaserrano.domain.bo.LocationBO
import com.mzaragozaserrano.domain.bo.MoreCharacteristicsBO
import com.mzaragozaserrano.domain.bo.MultimediaBO
import com.mzaragozaserrano.domain.bo.MultimediaDetailBO
import com.mzaragozaserrano.domain.bo.ParkingSpaceBO
import com.mzaragozaserrano.domain.bo.PriceBO
import com.mzaragozaserrano.domain.bo.PriceInfoBO

fun List<AdDTO>.transform(): List<AdBO> = map { it.transform() }

fun AdDTO.transform(): AdBO = AdBO(
    address = address,
    bathrooms = bathrooms,
    country = country,
    description = description,
    district = district,
    exterior = exterior,
    features = features?.transform(),
    floor = floor,
    latitude = latitude,
    longitude = longitude,
    multimedia = multimedia?.transform(),
    municipality = municipality,
    neighborhood = neighborhood,
    operation = operation,
    parkingSpace = parkingSpace?.transform(),
    price = price,
    priceInfo = priceInfo?.transform(),
    propertyCode = propertyCode,
    propertyType = propertyType,
    province = province,
    rooms = rooms,
    size = size,
    thumbnail = thumbnail
)

fun FeaturesDTO.transform(): FeaturesBO = FeaturesBO(
    hasAirConditioning = hasAirConditioning,
    hasBoxRoom = hasBoxRoom,
    hasGarden = hasGarden,
    hasSwimmingPool = hasSwimmingPool,
    hasTerrace = hasTerrace
)

fun MultimediaDTO.transform(): MultimediaBO = MultimediaBO(
    images = images?.map { it.transform() }
)

fun ImageDTO.transform(): ImageBO = ImageBO(url = url, tag = tag)

fun ParkingSpaceDTO.transform(): ParkingSpaceBO = ParkingSpaceBO(
    hasParkingSpace = hasParkingSpace,
    isParkingSpaceIncludedInPrice = isParkingSpaceIncludedInPrice
)

fun PriceInfoDTO.transform(): PriceInfoBO = PriceInfoBO(price = price?.transform())

fun AdDetailDTO.transform(): AdDetailBO = AdDetailBO(
    adid = adid,
    country = country,
    energyCertification = energyCertification?.transform(),
    extendedPropertyType = extendedPropertyType,
    homeType = homeType,
    moreCharacteristics = moreCharacteristics?.transform(),
    multimedia = multimedia?.transform(),
    operation = operation,
    price = price,
    priceInfo = priceInfo?.transform(),
    propertyComment = propertyComment,
    propertyType = propertyType,
    state = state,
    ubication = ubication?.transform()
)

fun EnergyCertificationDTO.transform(): EnergyCertificationBO = EnergyCertificationBO(
    emissions = emissions?.transform(),
    energyConsumption = energyConsumption?.transform(),
    title = title
)

fun EnergyTypeDTO.transform(): EnergyTypeBO = EnergyTypeBO(type = type)

fun MoreCharacteristicsDTO.transform(): MoreCharacteristicsBO = MoreCharacteristicsBO(
    agencyIsABank = agencyIsABank,
    bathNumber = bathNumber,
    boxroom = boxroom,
    communityCosts = communityCosts,
    constructedArea = constructedArea,
    energyCertificationType = energyCertificationType,
    exterior = exterior,
    flatLocation = flatLocation,
    floor = floor,
    housingFurnitures = housingFurnitures,
    isDuplex = isDuplex,
    lift = lift,
    modificationDate = modificationDate,
    roomNumber = roomNumber,
    status = status
)

fun MultimediaDetailDTO.transform(): MultimediaDetailBO = MultimediaDetailBO(
    images = images?.map { it.transform() }
)

fun ImageDetailDTO.transform(): ImageDetailBO = ImageDetailBO(
    url = url,
    tag = tag,
    localizedName = localizedName,
    multimediaId = multimediaId
)

fun UbicationDTO.transform(): LocationBO = LocationBO(
    latitude = latitude,
    longitude = longitude
)

fun PriceDTO.transform(): PriceBO = PriceBO(
    amount = amount,
    currencySuffix = currencySuffix
)

fun ErrorDTO.transform(): ErrorBO = when (this) {
    is ErrorDTO.Connectivity -> ErrorBO.Connectivity
    is ErrorDTO.DataNotFound -> ErrorBO.DataNotFound
    is ErrorDTO.DeserializingJSON -> ErrorBO.DeserializingJSON
    is ErrorDTO.Generic -> ErrorBO.Generic(id = id)
    is ErrorDTO.LoadingData -> ErrorBO.LoadingData
    is ErrorDTO.LoadingURL -> ErrorBO.LoadingURL
}