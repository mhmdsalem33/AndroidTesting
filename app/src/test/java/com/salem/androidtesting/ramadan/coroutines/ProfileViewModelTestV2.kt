package com.salem.testing

import app.cash.turbine.test
import com.salem.testing.flows.GetUserProfileUseCaseV2
import com.salem.testing.flows.ProfileUIState
import com.salem.testing.flows.ProfileViewModelV2
import com.salem.testing.flows.TestingUtils
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Test
    fun `test success scenario`() = runTest{

        //Given
        val useCase :  GetUserProfileUseCaseV2 = mockk()
        coEvery { useCase.getProfileData() } coAnswers { flowOf(Result.success(TestingUtils.profileDummyData)) }

        //when
        val viewModel = ProfileViewModelV2(useCase)
        viewModel.getUserProfileWayTwo()

//        advanceUntilIdle() // if i will enable it mush be a comment a state Idle and State Loading , Observe a last state only

        //then
        viewModel.profileUiState.test {
              awaitItem() shouldBe ProfileUIState.Idle     // state Idle
              awaitItem() shouldBe ProfileUIState.Loading  // state loading

            val successState = (awaitItem() as ProfileUIState.Success)
            successState.data shouldBe TestingUtils.profileDummyData

        }
    }



    @Test
    fun `test failure scenario`() = runTest{

        //Given
        val useCase : GetUserProfileUseCaseV2 = mockk()
        coEvery { useCase.getProfileData() } coAnswers { flow { throw IOException("Opps!") }}

        //When
        val viewModel = ProfileViewModelV2(useCase)
        viewModel.getUserProfileWayTwo()

        //Then
        viewModel.profileUiState.test {
             awaitItem() shouldBe ProfileUIState.Idle
             awaitItem() shouldBe ProfileUIState.Loading
            (awaitItem() as ProfileUIState.Error).message shouldBe "Opps!"
        }

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