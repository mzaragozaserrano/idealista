package com.mzaragozaserrano.idealista.data.datasources.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mzaragozaserrano.data.datasources.local.database.FavoritesDataSource
import com.mzaragozaserrano.data.datasources.local.database.FavoritesDatabase
import com.mzaragozaserrano.data.dto.AdDTO
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class FavoritesDataSourceTest {

    private lateinit var database: FavoritesDatabase
    private lateinit var favoritesDataSource: FavoritesDataSource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, FavoritesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        favoritesDataSource = database.favoritesDataSource()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insert favorite ad and retrieve it`() = runBlocking {
        // Given
        val ad = AdDTO(
            propertyCode = "1",
            thumbnail = "https://img4.idealista.com/image.webp",
            floor = "2",
            price = 1195000.0,
            propertyType = "flat",
            operation = "sale",
            size = 133.0,
            exterior = false,
            rooms = 3,
            bathrooms = 2,
            address = "calle de Lagasca",
            province = "Madrid",
            municipality = "Madrid",
            district = "Barrio de Salamanca",
            country = "es",
            neighborhood = "Castellana",
            latitude = 40.4362687,
            longitude = -3.6833686
        )

        // When
        favoritesDataSource.addFavorite(ad = ad)
        val favorites = favoritesDataSource.getAllFavorites()

        // Then
        assertEquals(1, favorites.size)
        assertEquals(ad.propertyCode, favorites.first().propertyCode)
    }

    @Test
    fun `delete all favorites and check database is empty`() = runBlocking {
        // Given
        val ad = AdDTO(
            id = 1,
            propertyCode = "1",
            thumbnail = "https://img4.idealista.com/image.webp",
            floor = "2",
            price = 1195000.0,
            propertyType = "flat",
            operation = "sale",
            size = 133.0,
            exterior = true,
            rooms = 3,
            bathrooms = 2,
            address = "calle de Lagasca",
            province = "Madrid",
            municipality = "Madrid",
            district = "Barrio de Salamanca",
            country = "es",
            neighborhood = "Castellana",
            latitude = 40.4362687,
            longitude = -3.6833686
        )
        favoritesDataSource.addFavorite(ad = ad)

        // When
        favoritesDataSource.removeFavorite(ad = ad)
        val favorites = favoritesDataSource.getAllFavorites()

        // Then
        assertTrue(favorites.isEmpty())
    }

    @Test
    fun `delete all favorites and check database is not empty`() = runBlocking {
        // Given
        val ad = AdDTO(
            propertyCode = "1",
            thumbnail = "https://img4.idealista.com/image.webp",
            floor = "2",
            price = 1195000.0,
            propertyType = "flat",
            operation = "sale",
            size = 133.0,
            exterior = true,
            rooms = 3,
            bathrooms = 2,
            address = "calle de Lagasca",
            province = "Madrid",
            municipality = "Madrid",
            district = "Barrio de Salamanca",
            country = "es",
            neighborhood = "Castellana",
            latitude = 40.4362687,
            longitude = -3.6833686
        )
        favoritesDataSource.addFavorite(ad = ad)

        // When
        favoritesDataSource.removeFavorite(ad = ad)
        val favorites = favoritesDataSource.getAllFavorites()

        // Then
        assertTrue(favorites.isNotEmpty())
    }

}