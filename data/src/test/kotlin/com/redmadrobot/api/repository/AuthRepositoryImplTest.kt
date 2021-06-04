package com.redmadrobot.api.repository

import com.redmadrobot.basetest.*
import com.redmadrobot.data.network.auth.AuthApi
import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.data.repository.UserProfileDataStorage
import com.redmadrobot.domain.repository.SessionRepository
import com.redmadrobot.mapmemory.MapMemory
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import retrofit2.Response

class AuthRepositoryImplTest : FreeSpec({

    Feature("User logout from network") {
        // region Fields and functions
        lateinit var testCoroutineScope: TestCoroutineScope
        lateinit var repository: AuthRepositoryImpl

        val mockAuthApi = mockk<AuthApi>()
        val mockSessionRepository = mockk<SessionRepository>()
        val mockUserProfileDataStorage = mockk<UserProfileDataStorage>()
        val memory = MapMemory()

        beforeEachScenario {
            testCoroutineScope = TestCoroutineScope()

            clearAllMocks()

            repository = AuthRepositoryImpl(
                mockAuthApi,
                mockSessionRepository,
                memory,
                mockUserProfileDataStorage
            )
        }
        afterEachScenario {
            testCoroutineScope.cleanupTestCoroutines()
        }
        // endregion

        Scenario("Logout clears the data regardless of the api results") {
            Given("Mock auth api logout throws Exceptions") {
                coEvery { mockAuthApi.logout(any()) } throws Exception("this is a test")
                every { mockSessionRepository.clear() } returns Unit
                every { mockSessionRepository.getAccessToken() } returns "access_token"
                every { mockUserProfileDataStorage.clear() } returns Unit
            }
            When("Logout") {
                testCoroutineScope.launch {
                    repository.logout()
                        .catch {}
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
                    memory.isEmpty() shouldBe true
                }
            }
        }
        Scenario("Logout clears the data on the backend too") {
            Given("Mock auth api logout returns Response.success") {
                coEvery { mockAuthApi.logout(any()) } returns Response.success(Unit)
                every { mockSessionRepository.clear() } returns Unit
                every { mockSessionRepository.getAccessToken() } returns "access_token"
                every { mockUserProfileDataStorage.clear() } returns Unit
            }
            When("Logout") {
                testCoroutineScope.launch {
                    repository.logout()
                        .catch {}
                        .single()
                }
            }
            Then("All data deleted") {
                assertSoftly {
                    coVerify {
                        mockAuthApi.logout(any())
                        mockSessionRepository.clear()
                        mockUserProfileDataStorage.clear()
                    }
                    memory.isEmpty() shouldBe true
                }
            }
        }
    }
})
