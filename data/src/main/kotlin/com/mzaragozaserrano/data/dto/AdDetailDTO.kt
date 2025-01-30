package com.mzaragozaserrano.data.dto

data class AdDetailDTO(
    val adid: Long? = null,
    val price: Double? = null,
    val priceInfo: PriceDetailDTO? = null,
    val operation: String? = null,
    val propertyType: String? = null,
    val extendedPropertyType: String? = null,
    val homeType: String? = null,
    val state: String? = null,
    val multimedia: MultimediaDTO? = null,
    val propertyComment: String? = null,
    val ubication: UbicationDTO? = null,
    val country: String? = null,
    val moreCharacteristics: MoreCharacteristicsDTO? = null,
    val energyCertification: EnergyCertificationDTO? = null,
)