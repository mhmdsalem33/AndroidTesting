package com.salem.testing

import com.salem.testing.flows.Friend
import com.salem.testing.flows.GetUserProfileUseCaseV2
import com.salem.testing.flows.Profile
import com.salem.testing.flows.TestingUtils.profileDummyData
import com.salem.testing.flows.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserProfileV2Test {


    @Test
    fun `Get Profile data , flow emits successfully`() = runTest{

        val repo : UserRepository = mockk()

        val useCase = GetUserProfileUseCaseV2(repo , this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher )

        val profileData = profileDummyData

        coEvery { repo.getName() } coAnswers {  "mohamed" }
        coEvery { repo.getRate() } coAnswers  {
            delay(1000)
            4.8f
        }
        coEvery { repo.getFriends() } coAnswers {
            delay(1000)
            listOf(
                Friend(id =  "1" , userName = "mohamed"),
                Friend(id =  "2" , userName = "hatem"),
            )
        }

        val flow = useCase.getProfileData()

        flow.collect { result ->
            result.isSuccess.shouldBeTrue()
            result.onSuccess { profile : Profile ->
                println("PROFILE IS $profile")
                profile shouldBeEqualTo  profileData
            }
        }
    }


    @Test
    fun `Get Profile Data , should retry with error`() = runTest{
        val repo : UserRepository = mockk()

        val useCase = GetUserProfileUseCaseV2( repo , this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher )

        coEvery { repo.getName() } coAnswers {  "mohamed" }

        coEvery { repo.getRate() } coAnswers  {
            delay(1000)
            4.8f
        }

        coEvery { repo.getFriends() } coAnswers {
         throw Exception("error")
        }


        val flow = useCase.getProfileData()

        flow.collect{
            it.isFailure shouldBeEqualTo true
        }
    }


    @Test
    fun `Get Profile data, should retry with success`() = runTest {
        val repo : UserRepository = mockk()
        val useCase = GetUserProfileUseCaseV2( repo , this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher )


        //Mock
        var throwError = true
        val profileData = profileDummyData


        coEvery { repo.getName() } coAnswers {
            if (throwError) throw Exception("error") else profileData.name
        }

        coEvery { repo.getRate() } coAnswers {
            if (throwError) repo.getRate() else profileData.rate
        }

        coEvery { repo.getFriends() } coAnswers {
            if (throwError) throw Exception("error") else profileData.friends
        }

        val flow = useCase.getProfileData()

        launch {
            flow.collect{
                assert(it.isSuccess)
            }
        }

        advanceTimeBy(1000)
        throwError = false
        advanceTimeBy(1000)

    }

}
