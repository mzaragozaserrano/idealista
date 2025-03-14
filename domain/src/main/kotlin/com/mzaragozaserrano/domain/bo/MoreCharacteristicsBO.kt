package com.mzaragozaserrano.domain.bo

data class MoreCharacteristicsBO(
    val agencyIsABank: Boolean,
    val bathNumber: Int?,
    val boxroom: Boolean,
    val communityCosts: Double?,
    val constructedArea: Double?,
    val energyCertificationType: String?,
    val exterior: String?,
    val flatLocation: String?,
    val floor: String?,
    val housingFurnitures: String?,
    val isDuplex: Boolean,
    val lift: String,
    val modificationDate: Long?,
    val roomNumber: Int?,
    val status: String?
)
