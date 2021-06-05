package com.redmadrobot.api.repository

import com.redmadrobot.basetest.*
import com.redmadrobot.basetest.fakes.SharedPreferencesFake
import com.redmadrobot.data.repository.SessionRepositoryImpl
import com.redmadrobot.domain.entity.repository.Tokens
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class SessionRepositoryImplTest : FreeSpec({

    Feature("Storing access token in memory, refresh token in SharedPreferences") {
        // region Fields and functions
        lateinit var repository: SessionRepositoryImpl

        beforeEachScenario {
            repository = SessionRepositoryImpl(
                SharedPreferencesFake()
            )
        }
        // endregion
        Scenario("Get tokens when they are saved, and then cleanup repository") {
            val accessToken = "access"
            val refreshToken = "refresh"

            When("Save new session") {
                repository.saveSession(Tokens(accessToken, refreshToken))
            }
            Then("Get tokens, they match the saved ones") {
                assertSoftly {
                    repository.sessionExists().shouldBeTrue()
                    repository.getAccessToken() shouldBe accessToken
                    repository.getRefreshToken() shouldBe refreshToken
                }
            }
            When("Clean up repository") {
                repository.clear()
            }
            Then("Get tokens from repository, they null") {
                repository.getAccessToken() shouldBe null
                repository.getRefreshToken() shouldBe null
            }
        }
        Scenario("Get tokens without saved session") {
            var accessToken: String? = null
            var refreshToken: String? = null

            When("Get tokens") {
                accessToken = repository.getAccessToken()
                refreshToken = repository.getRefreshToken()
            }
            Then("Both tokens null") {
                assertSoftly {
                    repository.sessionExists().shouldBeFalse()
                    accessToken shouldBe null
                    refreshToken shouldBe null
                }
            }
        }
    }
})
