package com.example.interviewprep.data.repository

import android.util.Log
import com.example.interviewprep.data.model.User
import com.example.interviewprep.data.remote.UserApi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserRepositoryTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userApi: UserApi

    @Before
    fun setup(){
        userApi = mockk()
        userRepository = UserRepository(userApi)
        mockkStatic("android.util.Log")
        every { Log.e(any(), any()) } returns 0
    }

    @Test
    fun `getUsers should return list of users`() = runTest {
        val fakeUsers = listOf(User(1, "John Doe", "john@example.com", "avatar.jpg"))
        coEvery { userApi.getUsers() } returns Response.success(fakeUsers)

        val result = userRepository.getUsers().first()

        assertEquals(fakeUsers, result)
    }

    @Test
    fun `getUsers should return an empty list when API fails`() = runTest {
        coEvery { userApi.getUsers() } returns Response.error(500, "".toResponseBody())

        val result = userRepository.getUsers().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getUserById should return the correct user`() = runTest {
        val fakeUser = User(1, "John Doe", "john@example.com", "avatar.jpg")
        coEvery { userApi.getUserById(1) } returns Response.success(fakeUser)

        val result = userRepository.getUserById(1).first()

        assertEquals(fakeUser, result)
    }

    @Test
    fun `getUserById should return null when user not found`() = runTest {
        coEvery { userApi.getUserById(1) } returns Response.error(404, "".toResponseBody())

        val result = userRepository.getUserById(1).first()

        assertNull(result)
    }

    @Test
    fun `createUser should return true when successful`() = runTest {
        val user = User(2, "Jane Doe", "jane@exmaple.com", "avatar.jpg")
        coEvery { userApi.createUser(user) } returns Response.success(user)

        val result = userRepository.createUser(user)
        assertTrue(result)
    }

    @Test
    fun `updateUser should return true when succesful`() = runTest {
        val user = User(1, "John Doe", "john@example.com", "avatar.jpg")
        coEvery { userApi.updateUser(1, user) } returns Response.success(null)

        val result = userRepository.updateUser(1, user)
        assertTrue(result)
    }

    @Test
    fun `deleteUser should return false when API fails`() = runTest {
        coEvery { userApi.deleteUser(1) } returns Response.error(500, "".toResponseBody())

        val result = userRepository.deleteUser(1)

        assertFalse(result)
    }
}