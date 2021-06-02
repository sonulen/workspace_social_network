package com.redmadrobot.api.repository

import android.content.SharedPreferences
import com.redmadrobot.basetest.*
import com.redmadrobot.data.repository.SessionRepositoryImpl
import com.redmadrobot.domain.entity.repository.Tokens
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SessionRepositoryImplTest : FreeSpec({

    Feature("Storing access token in memory, refresh token in SharedPreferences") {
        // region Fields and functions
        val mockSharedPreference = mockk<SharedPreferences>()
        val mockEditor = mockk<SharedPreferences.Editor>()
        lateinit var repository: SessionRepositoryImpl

        beforeEachScenario {
            clearAllMocks()
            every { mockSharedPreference.edit() } returns mockEditor

            repository = SessionRepositoryImpl(
                mockSharedPreference
            )
        }
        // endregion
        Scenario("Get tokens when they are saved, and then cleanup repository") {
            val key = "AUTH_REFRESH_TOKEN"
            val accessToken = "access"
            val refreshToken = "refresh"

            Given("Mock SharedPreferences and Editor") {
                every { mockSharedPreference.getString(key, null) } returns refreshToken andThen null
                every { mockEditor.putString(key, refreshToken) } returns mockEditor
                every { mockEditor.remove(key) } returns mockEditor
                every { mockEditor.apply() } returns Unit
            }
            When("Save new session") {
                repository.saveSession(Tokens(accessToken, refreshToken))
            }
            Then("Refresh token must be stored in SharedPreferences") {
                assertSoftly {
                    verifySequence {
                        mockEditor.putString(key, refreshToken)
                        mockEditor.apply()
                    }
                }
            }
            And("Get tokens, they match the saved ones") {
                repository.getAccessToken() shouldBe accessToken
                repository.getRefreshToken() shouldBe refreshToken
                verify {
                    mockSharedPreference.getString(key, null)
                }
            }
            Then("Clean up repository") {
                repository.clear()
            }
            And("Get tokens from repository, they null") {
                verify {
                    mockEditor.remove(key)
                }
                repository.getAccessToken() shouldBe null
                repository.getRefreshToken() shouldBe null
            }
        }
        Scenario("Get tokens without set its returns null") {
            val key = "AUTH_REFRESH_TOKEN"
            var accessToken: String? = null
            var refreshToken: String? = null

            Given("Mock SharedPreferences.Editor") {
                every { mockSharedPreference.getString(key, null) } returns null
            }
            When("Save new session") {
                accessToken = repository.getAccessToken()
                refreshToken = repository.getRefreshToken()
            }
            Then("Both tokens null") {
                assertSoftly {
                    accessToken shouldBe null
                    refreshToken shouldBe null
                }
            }
        }
    }
})
