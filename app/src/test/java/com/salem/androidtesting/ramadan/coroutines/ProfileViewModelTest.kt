package com.salem.androidtesting.ramadan.coroutines

import com.salem.androidtesting.ramadan.coroutines.use_case.TestingUtils.dummyProfileData
import com.salem.androidtesting.ramadan_test.coroutines.UseCase.GetUserProfileUseCase
import com.salem.androidtesting.ramadan_test.coroutines.viewmodel.ProfileUIState
import com.salem.androidtesting.ramadan_test.coroutines.viewmodel.ProfileViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {


    @get:Rule
    val mainDispatcherRule =  MainDispatcherRule()

    @Test
    fun `test success scenario`() = runTest{

        // given
        val getUserProfileUseCase: GetUserProfileUseCase = mockk() // مدام استخدمت mockk لازم اعمل   stubbing
        coEvery { getUserProfileUseCase.getProfileDataAsync() } coAnswers { dummyProfileData }

        val viewModel = ProfileViewModel(getUserProfileUseCase)

        // when
        viewModel.getUserProfile()

        advanceUntilIdle()

        // then
        assertEquals(ProfileUIState.Success(dummyProfileData) , viewModel.profileUiState.value)

    }

    @Test
    fun `test failure scenario`() = runTest{
        // given
        val getUserProfileUseCase : GetUserProfileUseCase = mockk()
        coEvery { getUserProfileUseCase.getProfileDataAsync() } throws IllegalStateException("Error")

        val viewModel = ProfileViewModel(getUserProfileUseCase)

        // when
        viewModel.getUserProfile()
        advanceUntilIdle()

        assertEquals(ProfileUIState.Error("Error"), viewModel.profileUiState.value)

    }

}


@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule( val testDispatcherRule : TestDispatcher = StandardTestDispatcher()) : TestWatcher(){
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcherRule)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}