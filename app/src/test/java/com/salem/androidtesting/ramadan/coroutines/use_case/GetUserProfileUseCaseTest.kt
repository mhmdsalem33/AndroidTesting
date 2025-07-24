package com.salem.androidtesting.ramadan.coroutines.use_case

import com.salem.androidtesting.ramadan_test.UseCase.Friend
import com.salem.androidtesting.ramadan_test.UseCase.GetUserProfileUseCase
import com.salem.androidtesting.ramadan_test.UseCase.Profile
import com.salem.androidtesting.ramadan_test.UseCase.UserRepository
import com.salem.androidtesting.ramadan_test.UseCase.UserRepositoryImpl
import com.salem.androidtesting.ramadan_test.UseCase.UserRepositoryImplV2WorkParallel
import com.salem.androidtesting.ramadan_test.UseCase.UserRepositoryImplV2WorkSequential
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserProfileUseCaseTest {

    @Test
    fun `Given happy scenario when call getProfileDataAsync() then the profile should be constructed correctly`() =
        runTest {

            // Given
            val userRepositoryImpl = UserRepositoryImpl()
            val getUserUseCase = GetUserProfileUseCase(userRepositoryImpl)

            // When
            val result = getUserUseCase.getProfileDataAsync()

            // Then
            assertEquals(TestingUtils.dummyProfileData, result)

        }

    @Test
    fun `Given happy scenario when calling getProfileDataAsync() then i should expect parallel calling`() =
        runTest {

            // Given
            val userRepositoryImplV2WorkParallel = UserRepositoryImplV2WorkParallel()
            val getUserProfileUseCase = GetUserProfileUseCase(userRepositoryImplV2WorkParallel)

            // When
            val result = getUserProfileUseCase.getProfileDataAsync()

            // then
            assertEquals(
                1000,
                currentTime
            ) // result will success and calling will take a 1 sec only

        }


    @Test
    fun `Given happy scenario when calling getProfileDataAsync() then i should expect sequential calling`() =
        runTest {

            // Given
            val userRepositoryImplV2WorkSequential = UserRepositoryImplV2WorkSequential()
            val getUserProfileUseCase = GetUserProfileUseCase(userRepositoryImplV2WorkSequential)

            // When
            val result = getUserProfileUseCase.getProfileDataSequential()

            // then
            assertEquals(
                3000,
                currentTime
            ) // result will success and calling will take a 3 sec only

        }


    @Test
    fun `test Mocks with coroutines`() = runTest {

        // Given
        val userRepo : UserRepository = mockk()
        val useCase = GetUserProfileUseCase(userRepo)


        coEvery { userRepo.getName() } coAnswers {
            delay(1000)
            "mohamed"
        }

        coEvery { userRepo.getRate() } coAnswers {
            delay(1000)
            4.8f
        }

        coEvery { userRepo.getFriends() } coAnswers {
            delay(1000)
            listOf(
                Friend(id = "1", userName = "mohamed"),
                Friend(id = "2", userName = "hatem")
            )
        }

        //when
        val result = useCase.getProfileDataSequential()

        //then
        assertEquals(3000 , currentTime)

    }


    @Test
    fun `test Mocks with virtual coroutines`() = runTest {

        // Given
        val userRepo : UserRepository = mockk()
        val useCase = GetUserProfileUseCase( userRepo  , this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)


        coEvery { userRepo.getName() } coAnswers { delay(1000)
            "mohamed"
        }

        coEvery { userRepo.getRate() } coAnswers { delay(1000)
            4.8f
        }

        coEvery { userRepo.getFriends() } coAnswers {  delay(1000)
            listOf(
                Friend(id = "1", userName = "mohamed"),
                Friend(id = "2", userName = "hatem")
            )
        }

        //when
        val result = useCase.getProfileWithVirtualCoroutines()

        //then
        assertEquals(3000 , currentTime)
    }


}

object TestingUtils {
    val dummyProfileData = Profile(
        name = "mohamed",
        rate = 4.8f,
        friends = listOf(
            Friend(id = "1", userName = "mohamed"),
            Friend(id = "2", userName = "hatem")
        )
    )
}
