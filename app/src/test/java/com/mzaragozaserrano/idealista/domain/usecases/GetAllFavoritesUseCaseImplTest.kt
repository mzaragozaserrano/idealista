package com.mzaragozaserrano.idealista.domain.usecases

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzaragozaserrano.domain.usecases.GetAllFavoritesUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllFavoritesUseCaseImplTest {

    private val favoritesRepository: FavoritesRepository = mockk(relaxed = true)

    lateinit var getAllFavorites: GetAllFavoritesUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getAllFavorites = GetAllFavoritesUseCaseImpl(favoritesRepository = favoritesRepository)
    }

    @Test
    fun `when API returns data, the use case should return the same data`() = runBlocking {
        // Given
        val expectedList = listOf(
            mockk<AdBO>(relaxed = true),
            mockk<AdBO>(relaxed = true)
        )
        coEvery { favoritesRepository.getAllFavorites() } returns expectedList

        // When
        val result = getAllFavorites()

        // Then
        coVerify(exactly = 1) { favoritesRepository.getAllFavorites() }
        assertEquals(expectedList, result)
    }

    @Test
    fun `when API returns an empty list, the use case should return an empty list`() = runBlocking {
        // Given
        coEvery { favoritesRepository.getAllFavorites() } returns emptyList()

        // When
        val result = getAllFavorites()

        // Then
        coVerify(exactly = 1) { favoritesRepository.getAllFavorites() }
        assertTrue(result.isEmpty())
    }

    @Test
    fun `when API throws an error, the use case should propagate the exception`() = runBlocking {
        // Given
        val expectedMessage = "Network error"
        coEvery { favoritesRepository.getAllFavorites() } throws RuntimeException(expectedMessage)

        // When
        var exception: Exception? = null
        try {
            getAllFavorites()
        } catch (e: Exception) {
            exception = e
        }

        // Then
        assertNotNull(exception)
        assertTrue(exception is RuntimeException)
        assertEquals(expectedMessage, exception?.message)
    }

}