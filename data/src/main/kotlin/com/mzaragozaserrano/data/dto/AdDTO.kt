package com.mzaragozaserrano.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "favorite_table")
data class AdDTO(
    val address: String? = null,
    val bathrooms: Int? = null,
    val country: String? = null,
    val date: String? = null,
    val description: String? = null,
    val district: String? = null,
    val exterior: Boolean? = null,
    val features: FeaturesDTO? = null,
    val floor: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val multimedia: MultimediaDTO? = null,
    val municipality: String? = null,
    val neighborhood: String? = null,
    val operation: String? = null,
    val parkingSpace: ParkingSpaceDTO? = null,
    val price: Double? = null,
    val priceInfo: PriceInfoDTO? = null,
    val propertyCode: String? = null,
    val propertyType: String? = null,
    val province: String? = null,
    val rooms: Int? = null,
    val size: Double? = null,
    val thumbnail: String? = null,
)

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromFeaturesDTO(value: FeaturesDTO?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toFeaturesDTO(value: String?): FeaturesDTO? {
        return value?.let { gson.fromJson(it, FeaturesDTO::class.java) }
    }

    @TypeConverter
    fun fromMultimediaDTO(value: MultimediaDTO?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toMultimediaDTO(value: String?): MultimediaDTO? {
        return value?.let { gson.fromJson(it, MultimediaDTO::class.java) }
    }

    @TypeConverter
    fun fromImageDTOList(value: List<ImageDTO>?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toImageDTOList(value: String?): List<ImageDTO>? {
        return value?.let {
            val listType = object : TypeToken<List<ImageDTO>>() {}.type
            gson.fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromParkingSpaceDTO(value: ParkingSpaceDTO?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toParkingSpaceDTO(value: String?): ParkingSpaceDTO? {
        return value?.let { gson.fromJson(it, ParkingSpaceDTO::class.java) }
    }

    @TypeConverter
    fun fromPriceInfoDTO(value: PriceInfoDTO?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toPriceInfoDTO(value: String?): PriceInfoDTO? {
        return value?.let { gson.fromJson(it, PriceInfoDTO::class.java) }
    }
}