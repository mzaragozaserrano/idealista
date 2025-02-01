package com.mzaragozaserrano.presentation.vo

import com.mzaragozaserrano.domain.bo.EnergyCertificationBO
import com.mzaragozaserrano.domain.bo.MoreCharacteristicsBO
import com.mzaragozaserrano.domain.bo.MultimediaDetailBO
import com.mzaragozaserrano.domain.bo.UbicationBO

data class DetailedAdVO(
    val energyCertification: EnergyCertificationBO?,
    val moreCharacteristics: MoreCharacteristicsBO?,
    val multimedia: MultimediaDetailBO?,
    val propertyComment: String?,
    val ubication: UbicationBO?,
)