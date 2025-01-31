package com.mzaragozaserrano.domain.bo

data class EnergyCertificationBO(
    val emissions: EnergyTypeBO?,
    val energyConsumption: EnergyTypeBO?,
    val title: String?,
)