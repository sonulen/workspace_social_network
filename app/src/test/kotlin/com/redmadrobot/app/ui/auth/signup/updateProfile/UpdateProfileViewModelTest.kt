package com.redmadrobot.app.ui.auth.signup.updateProfile

import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.basetest.*
import com.redmadrobot.data.entity.api.response.NetworkEntityError
import com.redmadrobot.data.network.errors.NetworkException
import com.redmadrobot.domain.entity.repository.Tokens
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.util.AuthValidatorImpl
import com.redmadrobot.extensions.lifecycle.Event
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.beInstanceOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class UpdateProfileViewModelTest : FreeSpec({
    beforeSpec { TestLiveDataExecutionController.enableTestMode() }
    afterSpec { TestLiveDataExecutionController.disableTestMode() }

    Feature("Register user and update user profile data") {
        // region Fields and functions
        lateinit var testDispatcher: TestCoroutineDispatcher
        lateinit var viewModel: UpdateProfileViewModel

        val validator = AuthValidatorImpl()
        val mockAuthRepository = mockk<AuthRepository>()

        beforeEachScenario {
            testDispatcher = TestCoroutineDispatcher()
            Dispatchers.setMain(testDispatcher)

            clearAllMocks()

            viewModel = UpdateProfileViewModel(
                mockAuthRepository,
                validator,
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
                    birthDayError shouldNotBe null
                    birthDayError shouldBe R.string.invalid_birthday
                    isRegisterButtonEnabled shouldBe false
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
            When("Enter invalid birthday") {
                viewModel.onBirthDayEntered("1993-07-28")
            }
            Then("BirthDayError is null and register button is enabled") {
                assertSoftly {
                    birthDayError shouldBe null
                    isRegisterButtonEnabled shouldBe false
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
                isRegisterButtonEnabled shouldBe false
            }
        }

        Scenario("Pressing register button with invalid email") {
            // region Input data
            val nickname = "sonulen"
            val name = "Andrey"
            val surname = "Tolmachev"
            val birthDay = "0000-00-00"
            val errorMessage = "this is a test"
            // endregion
            var isRegisterButtonEnabled = false
            var event: Event? = null

            Given("Subscribe on button status and mock AuthRepository where updateProfile throw error") {
                every { mockAuthRepository.register(any(), any()) } returns flow {
                    emit(Tokens("access", "refresh"))
                }
                every {
                    mockAuthRepository.updateProfile(any(),
                        any(),
                        any(),
                        any())
                } returns flow {
                    throw NetworkException.BadRequest(
                        NetworkEntityError("111", errorMessage)
                    )
                }
                viewModel.isRegisterButtonEnabled.observeForever {
                    isRegisterButtonEnabled = it
                }
                viewModel.eventsQueue.observeForever {
                    event = it
                }
            }
            When("Enter fully valid data with invalid birthday") {
                viewModel.onNicknameEntered(nickname)
                viewModel.onNameEntered(name)
                viewModel.onSurnameEntered(surname)
                viewModel.onBirthDayEntered(birthDay)
                viewModel.onEmailAndPasswordReceived(
                    "noreply@gmail.com",
                    "bestPassword1"
                )
            }
            Then("Register button is disable") {
                isRegisterButtonEnabled shouldBe false
            }
            When("Click on register button with invalid birthday") {
                viewModel.onRegisterClicked(nickname, name, surname, birthDay)
            }
            Then("Correctly sequence of AuthRepository method's calls") {
                verifySequence {
                    mockAuthRepository.register(any(), any())
                    mockAuthRepository.updateProfile(any(), any(), any(), any())
                }
            }
            And("Error event with message") {
                event shouldNotBe null
                event should beInstanceOf<EventError>()
                (event as EventError).errorMessage shouldBe errorMessage
            }
        }

        Scenario("Check unlocking and pressing register button") {
            // region Input data
            val nickname = "sonulen"
            val name = "Andrey"
            val surname = "Tolmachev"
            val birthDay = "1993-07-29"
            // endregion
            var isRegisterButtonEnabled = false
            var event: Event? = null

            Given("Subscribe on button status and mock AuthRepository") {
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

                viewModel.isRegisterButtonEnabled.observeForever {
                    isRegisterButtonEnabled = it
                }
                viewModel.eventsQueue.observeForever {
                    event = it
                }
            }
            When("Enter fully valid data") {
                viewModel.onNicknameEntered(nickname)
                viewModel.onNameEntered(name)
                viewModel.onSurnameEntered(surname)
                viewModel.onBirthDayEntered(birthDay)
                viewModel.onEmailAndPasswordReceived(
                    "noreply@gmail.com",
                    "bestPassword1"
                )
            }
            Then("Register button is enable") {
                isRegisterButtonEnabled shouldBe true
            }
            When("Click on register button") {
                viewModel.onRegisterClicked(nickname, name, surname, birthDay)
            }
            Then("Correctly sequence of AuthRepository method's calls") {
                verifySequence {
                    mockAuthRepository.register(any(), any())
                    mockAuthRepository.updateProfile(any(), any(), any(), any())
                }
            }
            And("Navigation event to Done Fragment") {
                event shouldNotBe null
                event should beInstanceOf<EventNavigateTo>()
                (event as EventNavigateTo).direction shouldBe UpdateProfileFragmentDirections.toDoneFragment()
            }
        }
    }
})
