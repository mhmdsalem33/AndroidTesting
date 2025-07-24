package com.salem.androidtesting.ramadan_test.coroutines.UseCase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GetUserProfileUseCase( private val userRepository : UserRepository , private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO ) {



    suspend fun getProfileDataAsync() = coroutineScope {

        val name = async { userRepository.getName() }
        val rate = async { userRepository.getRate() }
        val friends = async { userRepository.getFriends() }

        Profile(
            name = name.await(),
            rate = rate.await(),
            friends = friends.await()
        )
    }


    suspend fun getProfileDataSequential() = coroutineScope {

        val name = userRepository.getName()
        val rate = userRepository.getRate()
        val friends = userRepository.getFriends()

        Profile(
            name = name,
            rate = rate,
            friends = friends
        )
    }

    suspend fun getProfileWithVirtualCoroutines() = withContext(ioDispatcher) {
        val name = userRepository.getName()
        val rate = userRepository.getRate()
        val friends = userRepository.getFriends()

        Profile(
            name = name,
            rate = rate,
            friends = friends
        )
    }


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

class UserRepositoryImplV2WorkParallel : UserRepository{
    override suspend fun getName(): String {
        delay(1000)
        return  "mohamed"
    }
    override suspend fun getFriends(): List<Friend> {
        delay(1000)
        return  listOf(
            Friend(id =  "1" , userName = "mohamed"),
            Friend(id =  "2" , userName = "hatem"),
        )
    }
    override suspend fun getRate(): Float  {
        delay(1000)
        return 4.8f
    }
}



class UserRepositoryImplV2WorkSequential : UserRepository{
    override suspend fun getName(): String {
        delay(1000)
        return  "mohamed"
    }
    override suspend fun getFriends(): List<Friend> {
        delay(1000)
        return  listOf(
            Friend(id =  "1" , userName = "mohamed"),
            Friend(id =  "2" , userName = "hatem"),
        )
    }
    override suspend fun getRate(): Float  {
        delay(1000)
        return 4.8f
    }
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