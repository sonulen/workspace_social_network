package com.redmadrobot.api.repository

import com.redmadrobot.basetest.*
import com.redmadrobot.data.network.auth.AuthApi
import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.data.repository.UserProfileDataStorage
import com.redmadrobot.domain.repository.SessionRepository
import com.redmadrobot.mapmemory.MapMemory
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthRepositoryImplTest : FreeSpec({

    Feature("User logout from network") {
        // region Fields and functions
        lateinit var repository: AuthRepositoryImpl
        lateinit var mockAuthApi: AuthApi
        lateinit var mockSessionRepository: SessionRepository
        lateinit var mockUserProfileDataStorage: UserProfileDataStorage
        lateinit var memory: MapMemory

        beforeEachScenario {

            mockAuthApi = mockk<AuthApi>()
            mockSessionRepository = mockk<SessionRepository>(relaxUnitFun = true)
            mockUserProfileDataStorage = mockk<UserProfileDataStorage>(relaxUnitFun = true)
            memory = MapMemory()

            repository = AuthRepositoryImpl(
                mockAuthApi,
                mockSessionRepository,
                memory,
                mockUserProfileDataStorage
            )
        }
        // endregion

        Scenario("Logout clears the data regardless of the api results") {
            Given("Mock auth api logout throws Exceptions") {
                coEvery { mockAuthApi.logout(any()) } throws Exception("this is a test")
                every { mockSessionRepository.getAccessToken() } returns "access_token"
            }
            When("Logout") {
                launch {
                    repository.logout()
                        .catch {
                            // No-op
                        }
                        .firstOrNull()
                }
            }
            Then("All data deleted") {
                assertSoftly {
                    coVerify {
                        mockAuthApi.logout(any())
                        mockSessionRepository.clear()
                        mockUserProfileDataStorage.clear()
                    }
                    memory.isEmpty().shouldBeTrue()
                }
            }
        }
        Scenario("Logout clears the data on the backend too") {
            Given("Mock auth api logout returns Response.success") {
                coEvery { mockAuthApi.logout(any()) } returns Response.success(Unit)
                every { mockSessionRepository.getAccessToken() } returns "access_token"
            }
            When("Logout") {
                launch {
                    repository.logout()
                        .catch {
                            // No-op
                        }
                        .first()
                }
            }
            Then("All data deleted") {
                assertSoftly {
                    coVerify {
                        mockAuthApi.logout(any())
                        mockSessionRepository.clear()
                        mockUserProfileDataStorage.clear()
                    }
                    memory.isEmpty().shouldBeTrue()
                }
            }
        }
    }
})
