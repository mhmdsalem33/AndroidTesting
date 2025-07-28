package com.salem.testing.flows
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

class GetUserProfileUseCaseV2(
    private val userRepository : UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {


    suspend fun getProfileData() = flow {
        println("Starting Flow")
        val name = userRepository.getName()
        val rate = userRepository.getRate()
        val friends = userRepository.getFriends()

       val profile =  Profile(
            name = name,
            rate = rate,
            friends = friends
        )
        emit(Result.success(profile))
    }.retry(2) {
        println("Starting Retry Flow")
        ( it is Exception ).also {
            println("Before Delay Flow")
            delay(1000)
            println("After Delay Flow")
        }
    }.catch {
        emit(Result.failure(it))
    }.flowOn(ioDispatcher)


}

interface UserRepository{
    suspend fun getName() : String
    suspend fun getFriends() : List<Friend>
    suspend fun getRate() : Float
}

class UserRepositoryImpl : UserRepository{
    override suspend fun getName(): String = "mohamed"
    override suspend fun getFriends(): List<Friend>  = listOf(
        Friend(id =  "1" , userName = "mohamed"),
        Friend(id =  "2" , userName = "hatem"),
    )
    override suspend fun getRate(): Float  = 4.8f
}








data class Profile(
    val name : String,
    val rate : Float,
    val friends : List<Friend>
)


data class Friend(
    val id : String,
    val userName : String
)

object TestingUtils{
    val profileDummyData = Profile(
        name = "mohamed",
        rate = 4.8f,
        friends = listOf(
            Friend(id =  "1" , userName = "mohamed"),
            Friend(id =  "2" , userName = "hatem"),
        )

    )
}