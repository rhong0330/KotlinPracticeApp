package com.example.interviewprep.data.repository

import com.example.interviewprep.data.model.User
import com.example.interviewprep.data.remote.UserApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userApi: UserApi

    @Before
    fun setup(){
        userApi = mockk()
        userRepository = UserRepository(userApi)
    }

    @Test
    fun `getUsers should return list of users`() = runTest {
        val fakeUsers = listOf(User(1, "John Doe", "john@example.com", "avatar.jpg"))
        coEvery { userApi.getUsers() } returns fakeUsers

        val result = userRepository.getUsers().first()

        assertEquals(fakeUsers, result)
    }
}