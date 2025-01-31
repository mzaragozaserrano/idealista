package com.mzaragozaserrano.data.dto

data class EnergyCertificationDTO(
    val emissions: EnergyTypeDTO? = null,
    val energyConsumption: EnergyTypeDTO? = null,
    val title: String? = null,
)