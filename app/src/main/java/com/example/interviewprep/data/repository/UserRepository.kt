package com.example.interviewprep.data.repository

import android.util.Log
import com.example.interviewprep.data.model.User
import com.example.interviewprep.data.remote.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: UserApi) {
    fun getUsers(): Flow<List<User>> = flow {
        val users = api.getUsers()
        emit(users)
    }.catch { e ->
        throw e
    }


    fun getUserById(userId: Int): Flow<User?> = flow {
        val user = api.getUserById(userId)
        emit(user)
    }.catch { e ->
        throw e
    }

    suspend fun createUser(user: User): Boolean {
        return try {
            val response = api.createUser(user)

            if (response.isSuccessful && response.body() != null) {
                true
            } else {
                Log.e("UserRepository", "Error creating user: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception creating user: ${e.message}")
            false
        }
    }

    suspend fun updateUser(userId: Int, user: User): Boolean {
        return try {
            api.updateUser(userId, user)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteUser(userId: Int): Boolean {
        return try {
            api.deleteUser(userId)
            true
        } catch (e: Exception) {
            false
        }
    }


}