package com.mzaragozaserrano.data.dto

data class MoreCharacteristicsDTO(
    val agencyIsABank: Boolean = false,
    val bathNumber: Int? = null,
    val boxroom: Boolean = false,
    val communityCosts: Double? = null,
    val constructedArea: Double? = null,
    val energyCertificationType: String? = null,
    val exterior: Boolean = false,
    val flatLocation: String? = null,
    val floor: String? = null,
    val housingFurnitures: String? = null,
    val isDuplex: Boolean = false,
    val lift: Boolean = false,
    val modificationDate: Long? = null,
    val roomNumber: Int? = null,
    val status: String? = null
)
