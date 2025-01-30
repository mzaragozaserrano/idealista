package com.mzaragozaserrano.data.dto

data class EnergyCertificationDTO(
    val title: String? = null,
    val energyConsumption: EnergyTypeDTO? = null,
    val emissions: EnergyTypeDTO? = null
)