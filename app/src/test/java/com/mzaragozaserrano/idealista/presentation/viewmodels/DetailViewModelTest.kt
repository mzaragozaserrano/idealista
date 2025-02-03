package com.mzaragozaserrano.idealista.presentation.viewmodels

import app.cash.turbine.test
import com.mzaragozaserrano.domain.bo.DetailedAdBO
import com.mzaragozaserrano.domain.bo.EnergyCertificationBO
import com.mzaragozaserrano.domain.bo.EnergyTypeBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.bo.ImageDetailBO
import com.mzaragozaserrano.domain.bo.MoreCharacteristicsBO
import com.mzaragozaserrano.domain.bo.MultimediaDetailBO
import com.mzaragozaserrano.domain.bo.PriceBO
import com.mzaragozaserrano.domain.bo.UbicationBO
import com.mzaragozaserrano.domain.usecases.AddFavoriteAdUseCaseImpl
import com.mzaragozaserrano.domain.usecases.GetDetailedAdUseCaseImpl
import com.mzaragozaserrano.domain.usecases.OpenGoogleMapsUseCaseImpl
import com.mzaragozaserrano.domain.usecases.RemoveFavoriteAdUseCaseImpl
import com.mzaragozaserrano.domain.utils.DomainStringResource
import com.mzaragozaserrano.idealista.utils.MainDispatcherRule
import com.mzaragozaserrano.presentation.base.BaseComposeViewModel
import com.mzaragozaserrano.presentation.utils.transform
import com.mzaragozaserrano.presentation.viewmodels.DetailViewModel
import com.mzaragozaserrano.presentation.vo.AdType
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.ErrorVO
import com.mzaragozaserrano.presentation.vo.Feature.AirConditioning
import com.mzaragozaserrano.presentation.vo.Feature.Parking
import com.mzaragozaserrano.presentation.vo.Feature.Terrace
import com.mzaragozaserrano.presentation.vo.Info
import com.mzs.core.domain.bo.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val addFavoriteAd: AddFavoriteAdUseCaseImpl = mockk(relaxed = true)
    private val getDetailedAd: GetDetailedAdUseCaseImpl = mockk(relaxed = true)
    private val openGoogleMaps: OpenGoogleMapsUseCaseImpl = mockk(relaxed = true)
    private val removeFavoriteAd: RemoveFavoriteAdUseCaseImpl = mockk(relaxed = true)

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = DetailViewModel(
            addFavoriteAd = addFavoriteAd,
            getDetailedAd = getDetailedAd,
            openGoogleMaps = openGoogleMaps,
            removeFavoriteAd = removeFavoriteAd
        )
        Dispatchers.setMain(dispatcher = mainDispatcherRule.testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getDetailedAd is invoked, loading result is emitted first, followed by a success result`() =
        runTest {
            //Given
            val ad = AdVO(
                currencySuffix = "€",
                date = "12/03/2025 00:00",
                extraInfo = listOf(
                    Info.Floor(value = "3", secondValue = "Con ascensor"),
                    Info.Rooms(value = "2"),
                    Info.SquareMeters(value = "85")
                ),
                features = listOf(
                    AirConditioning,
                    Terrace,
                    Parking
                ),
                hasNotInformation = false,
                id = "1",
                isFavorite = true,
                latitude = 40.416775,
                longitude = -3.703790,
                prefixTitle = DomainStringResource.Exterior,
                price = "250.000",
                subtitle = "Piso luminoso con terraza",
                thumbnail = "https://example.com/image.jpg",
                title = "Moderno piso en el centro",
                type = AdType.Sale
            )
            val detailedAd = DetailedAdBO(
                adid = 1,
                country = "Spain",
                energyCertification = EnergyCertificationBO(
                    emissions = EnergyTypeBO(type = "A+"),
                    energyConsumption = EnergyTypeBO(type = "B"),
                    title = "High Efficiency"
                ),
                extendedPropertyType = "Apartment",
                homeType = "Urban",
                moreCharacteristics = MoreCharacteristicsBO(
                    agencyIsABank = true,
                    bathNumber = 2,
                    boxroom = true,
                    communityCosts = 150.0,
                    constructedArea = 85.0,
                    energyCertificationType = "A",
                    exterior = "Yes",
                    flatLocation = "Top floor",
                    floor = "5th",
                    housingFurnitures = "Furnished",
                    isDuplex = false,
                    lift = "Yes",
                    modificationDate = 1681200000L,
                    roomNumber = 3,
                    status = "Available"
                ),
                multimedia = MultimediaDetailBO(
                    images = listOf(
                        ImageDetailBO(
                            localizedName = "Living Room",
                            multimediaId = 1,
                            tag = "interior",
                            url = "https://example.com/livingroom.jpg"
                        ),
                        ImageDetailBO(
                            localizedName = "Bedroom",
                            multimediaId = 2,
                            tag = "interior",
                            url = "https://example.com/bedroom.jpg"
                        )
                    )
                ),
                operation = "Sale",
                price = 250000.0,
                priceInfo = PriceBO(amount = 250000.0, currencySuffix = "€"),
                propertyComment = "Spacious apartment with great view.",
                propertyType = "Residential",
                state = "Available",
                ubication = UbicationBO(latitude = 40.7128, longitude = -74.0060)
            )
            coEvery { getDetailedAd() } returns flow {
                emit(Result.Response.Error(ErrorBO.LoadingURL))
                delay(50)
                emit(Result.Response.Success(detailedAd))
            }

            //When
            viewModel.onCreate(ad = ad)

            //Then
            viewModel.uiState.test {
                assertEquals(
                    BaseComposeViewModel.UiState(
                        error = ErrorVO.LoadingURL,
                        loading = false,
                        success = DetailViewModel.DetailVO(
                            ad = ad,
                            initAd = ad
                        )
                    ), awaitItem()
                )
                assertEquals(
                    BaseComposeViewModel.UiState(
                        error = null,
                        loading = false,
                        success = DetailViewModel.DetailVO(
                            ad = ad,
                            initAd = ad,
                            detailedAd = detailedAd.transform()
                        )
                    ), awaitItem()
                )
            }
        }

}