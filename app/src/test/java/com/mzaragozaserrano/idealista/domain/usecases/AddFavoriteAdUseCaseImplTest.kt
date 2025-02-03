package com.mzaragozaserrano.idealista.domain.usecases

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzaragozaserrano.domain.usecases.AddFavoriteAdUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddFavoriteAdUseCaseImplTest {

    private val favoritesRepository: FavoritesRepository = mockk(relaxed = true)

    lateinit var addFavoriteAdUseCase: AddFavoriteAdUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        addFavoriteAdUseCase = AddFavoriteAdUseCaseImpl(favoritesRepository = favoritesRepository)
    }

    @Test
    fun `when adding a favorite ad, should return true if repository returns true`() = runBlocking {
        // Given
        val ad = mockk<AdBO>(relaxed = true)
        val params = AddFavoriteAdUseCaseImpl.Params(ad = ad)
        coEvery { favoritesRepository.addFavorite(ad = ad) } returns true

        // When
        val result = addFavoriteAdUseCase(params = params)

        // Then
        coVerify(exactly = 1) { favoritesRepository.addFavorite(ad = ad) }
        assertTrue(result)
    }

    @Test
    fun `when adding a favorite ad, should return false if repository returns false`() =
        runBlocking {
            // Given
            val ad = mockk<AdBO>(relaxed = true)
            val params = AddFavoriteAdUseCaseImpl.Params(ad = ad)
            coEvery { favoritesRepository.addFavorite(ad = ad) } returns false

            // When
            val result = addFavoriteAdUseCase(params = params)

            // Then
            coVerify(exactly = 1) { favoritesRepository.addFavorite(ad = ad) }
            assertEquals(false, result)
        }

    @Test
    fun `when repository throws an error, the use case should propagate the exception`() =
        runBlocking {
            // Given
            val ad = mockk<AdBO>(relaxed = true)
            val params = AddFavoriteAdUseCaseImpl.Params(ad = ad)
            val expectedMessage = "Database error"
            coEvery { favoritesRepository.addFavorite(ad = ad) } throws RuntimeException(expectedMessage)

            // When
            var exception: Exception? = null
            try {
                addFavoriteAdUseCase(params = params)
            } catch (e: Exception) {
                exception = e
            }

            // Then
            assertTrue(exception is RuntimeException)
            assertEquals(expectedMessage, exception?.message)
        }

}