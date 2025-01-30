package com.mzaragozaserrano.data.dto

data class MoreCharacteristicsDTO(
    val communityCosts: Double? = null,
    val roomNumber: Int? = null,
    val bathNumber: Int? = null,
    val exterior: Boolean = false,
    val housingFurnitures: String? = null,
    val agencyIsABank: Boolean = false,
    val energyCertificationType: String? = null,
    val flatLocation: String? = null,
    val modificationDate: Long? = null,
    val constructedArea: Double? = null,
    val lift: Boolean = false,
    val boxroom: Boolean = false,
    val isDuplex: Boolean = false,
    val floor: String? = null,
    val status: String? = null
)
