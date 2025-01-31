package com.mzaragozaserrano.domain.bo

data class DetailedAdBO(
    val adid: Int?,
    val country: String?,
    val energyCertification: EnergyCertificationBO?,
    val extendedPropertyType: String?,
    val homeType: String?,
    val moreCharacteristics: MoreCharacteristicsBO?,
    val multimedia: MultimediaDetailBO?,
    val operation: String?,
    val price: Double?,
    val priceInfo: PriceBO?,
    val propertyComment: String?,
    val propertyType: String?,
    val state: String?,
    val ubication: UbicationBO?,
)
