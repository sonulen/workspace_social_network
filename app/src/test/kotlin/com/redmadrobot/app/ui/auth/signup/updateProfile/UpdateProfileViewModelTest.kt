package com.redmadrobot.app.ui.auth.signup.updateProfile

import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.basetest.*
import com.redmadrobot.domain.entity.repository.Tokens
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.util.AuthValidatorImpl
import com.redmadrobot.extensions.lifecycle.Event
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

class UpdateProfileViewModelTest : FreeSpec({
    beforeSpec { TestLiveDataExecutionController.enableTestMode() }
    afterSpec { TestLiveDataExecutionController.disableTestMode() }

    Feature("Register user and update user profile data") {
        // region Fields and functions
        lateinit var testDispatcher: TestCoroutineDispatcher
        lateinit var viewModel: UpdateProfileViewModel
        lateinit var mockAuthRepository: AuthRepository

        beforeEachScenario {
            testDispatcher = TestCoroutineDispatcher()
            Dispatchers.setMain(testDispatcher)

            mockAuthRepository = mockk<AuthRepository>()

            viewModel = UpdateProfileViewModel(
                mockAuthRepository,
                AuthValidatorImpl(),
            )
        }
        afterEachScenario {
            testDispatcher.cleanupTestCoroutines()
            Dispatchers.resetMain()
        }
        // endregion

        Scenario("Enter incorrect birthday") {
            var birthDayError: Int? = null
            var isRegisterButtonEnabled = false

            Given("Subscribe on birthday error") {
                viewModel.birthDayError.observeForever {
                    birthDayError = it
                }
                viewModel.isRegisterButtonEnabled.observeForever {
                    isRegisterButtonEnabled = it
                }
            }
            When("Enter invalid birthday") {
                viewModel.onBirthDayEntered("0000-00-00")
            }
            Then("Error string res id is correct") {
                assertSoftly {
                    birthDayError shouldBe R.string.invalid_birthday
                    isRegisterButtonEnabled.shouldBeFalse()
                }
            }
        }

        Scenario("Enter valid birthday") {
            var birthDayError: Int? = null
            var isRegisterButtonEnabled = false

            Given("Subscribe on birthday error") {
                viewModel.birthDayError.observeForever {
                    birthDayError = it
                }
                viewModel.isRegisterButtonEnabled.observeForever {
                    isRegisterButtonEnabled = it
                }
            }
            When("Enter valid birthday") {
                viewModel.onBirthDayEntered("1993-07-28")
            }
            Then("BirthDayError is null and register button disable") {
                assertSoftly {
                    birthDayError.shouldBeNull()
                    isRegisterButtonEnabled.shouldBeFalse()
                }
            }
        }

        Scenario("Enter valid data without email and password") {
            var isRegisterButtonEnabled = false

            Given("Subscribe on button status") {
                viewModel.isRegisterButtonEnabled.observeForever {
                    isRegisterButtonEnabled = it
                }
            }
            When("Enter correct nickname name surname and birthday") {
                viewModel.onNicknameEntered("sonulen")
                viewModel.onNameEntered("Andrey")
                viewModel.onSurnameEntered("Tolmachev")
                viewModel.onBirthDayEntered("1993-07-29")
            }
            Then("Register button is disable") {
                isRegisterButtonEnabled.shouldBeFalse()
            }
        }

        Scenario("Enter full data with invalid birthday") {
            var isRegisterButtonEnabled = false
            Given("Enter fully valid data with invalid birthday") {
                viewModel.onNicknameEntered("sonulen")
                viewModel.onNameEntered("Andrey")
                viewModel.onSurnameEntered("Tolmachev")
                viewModel.onBirthDayEntered("0000-00-00")
                viewModel.onEmailAndPasswordReceived(
                    "noreply@gmail.com",
                    "bestPassword1"
                )
            }
            And("Subscribe on button status") {
                viewModel.isRegisterButtonEnabled.observeForever {
                    isRegisterButtonEnabled = it
                }
            }
            Then("Register button is disable") {
                isRegisterButtonEnabled.shouldBeFalse()
            }
        }

        Scenario("Check unlocking and pressing register button") {
            var isRegisterButtonEnabled = false
            var event: Event? = null

            Given("Mock AuthRepository") {
                every { mockAuthRepository.register(any(), any()) } returns flow {
                    emit(Tokens("access", "refresh"))
                }
                every { mockAuthRepository.updateProfile(any(), any(), any(), any()) } returns flow {
                    emit(
                        UserProfileData(
                            id = "1",
                            firstName = "MockName",
                            lastName = "MockLastName",
                            nickname = "MockNickname",
                            avatarUrl = null,
                            birthDay = "2001-01-01"
                        )
                    )
                }
            }
            And("Subscribe on button status") {
                viewModel.isRegisterButtonEnabled.observeForever {
                    isRegisterButtonEnabled = it
                }
                viewModel.eventsQueue.observeForever {
                    event = it
                }
            }
            When("Enter fully valid data") {
                viewModel.onNicknameEntered("sonulen")
                viewModel.onNameEntered("Andrey")
                viewModel.onSurnameEntered("Tolmachev")
                viewModel.onBirthDayEntered("1993-07-29")
                viewModel.onEmailAndPasswordReceived(
                    "noreply@gmail.com",
                    "bestPassword1"
                )
            }
            Then("Register button is enable") {
                isRegisterButtonEnabled.shouldBeTrue()
            }
            When("Click on register button") {
                viewModel.onRegisterClicked()
            }
            Then("Correctly sequence of AuthRepository method's calls") {
                verifySequence {
                    mockAuthRepository.register(any(), any())
                    mockAuthRepository.updateProfile(any(), any(), any(), any())
                }
            }
            And("Navigation event to Done Fragment") {
                (event as? EventNavigateTo)?.direction shouldBe UpdateProfileFragmentDirections.toDoneFragment()
            }
        }
    }
})
