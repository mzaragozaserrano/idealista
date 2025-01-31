package com.mzaragozaserrano.data.dto

data class AdDetailDTO(
    val adid: Int? = null,
    val country: String? = null,
    val energyCertification: EnergyCertificationDTO? = null,
    val extendedPropertyType: String? = null,
    val homeType: String? = null,
    val moreCharacteristics: MoreCharacteristicsDTO? = null,
    val multimedia: MultimediaDetailDTO? = null,
    val operation: String? = null,
    val price: Double? = null,
    val priceInfo: PriceDTO? = null,
    val propertyComment: String? = null,
    val propertyType: String? = null,
    val state: String? = null,
    val ubication: UbicationDTO? = null,
)