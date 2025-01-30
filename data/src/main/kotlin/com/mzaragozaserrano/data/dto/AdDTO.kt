package com.mzaragozaserrano.data.dto

data class AdDTO(
    val propertyCode: String? = null,
    val thumbnail: String? = null,
    val floor: String? = null,
    val price: Double? = null,
    val priceInfo: PriceInfoDTO? = null,
    val propertyType: String? = null,
    val operation: String? = null,
    val size: Double? = null,
    val exterior: Boolean? = null,
    val rooms: Int? = null,
    val bathrooms: Int? = null,
    val address: String? = null,
    val province: String? = null,
    val municipality: String? = null,
    val district: String? = null,
    val country: String? = null,
    val neighborhood: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val description: String? = null,
    val multimedia: MultimediaDTO? = null,
    val parkingSpace: ParkingSpaceDTO? = null,
    val features: FeaturesDTO? = null
)