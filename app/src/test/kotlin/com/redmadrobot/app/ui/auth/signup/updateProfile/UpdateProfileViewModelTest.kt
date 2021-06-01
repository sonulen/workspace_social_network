package com.redmadrobot.app.ui.auth.signup.updateProfile

import com.redmadrobot.basetest.*
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.util.AuthValidatorImpl
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import io.kotest.core.test.Description

@ExperimentalCoroutinesApi
class UpdateProfileViewModelTest : FreeSpec({
    beforeSpec { TestLiveDataExecutionController.enableTestMode() }
    afterSpec { TestLiveDataExecutionController.disableTestMode() }

    Feature("Testing view model") {
        // region Fields and functions
        lateinit var testDispatcher: TestCoroutineDispatcher
        val validator = AuthValidatorImpl()
        val mockAuthRepository = mockk< AuthRepository>()
        lateinit var viewModel: UpdateProfileViewModel

        listener(
            EachScenarioTestListener(
                featureDescription = description(),
                beforeEachScenario = {
                    Dispatchers.setMain(testDispatcher)

                    viewModel = UpdateProfileViewModel(
                        mockAuthRepository,
                        validator,
                    )
                },
                afterEachScenario = {
                    testDispatcher.cleanupTestCoroutines()
                    Dispatchers.resetMain()
                }
            )
        )

        // endregion
        Scenario("Enter invalid birthday") {
            When("Enter birthday") {
                viewModel.onBirthDayEntered("0000-00-00")
            }
            Then("Check view state") {
                viewModel.birthDayError shouldNotBe null
                viewModel.birthDayError
            }
        }
    }
})
