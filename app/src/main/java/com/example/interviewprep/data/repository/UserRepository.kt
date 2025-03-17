package com.example.interviewprep.data.repository

import android.util.Log
import com.example.interviewprep.data.model.User
import com.example.interviewprep.data.remote.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: UserApi) {
    fun getUsers(): Flow<List<User>> = flow {
        val response = api.getUsers()
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            throw Exception("API Error: ${response.code()} - ${response.message()}")
        }
    }.catch { e ->
        Log.e("UserRepository", "Error fetching users: ${e.message}")
        emit(emptyList())
    }


    fun getUserById(userId: Int): Flow<User?> = flow {
        val response = api.getUserById(userId)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            throw Exception("API Error: ${response.code()} - ${response.message()}")
        }
    }.catch { e ->
        Log.e( "UserRepository", "Error fetching userById: ${e.message}")
        emit(null)
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
            val response = api.updateUser(userId, user)
            if (response.isSuccessful) {
                true
            } else {
                Log.e("UserRepository", "Error updating user: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception updating user: ${e.message}")
            false
        }
    }

    suspend fun deleteUser(userId: Int): Boolean {
        return try {
            val response = api.deleteUser(userId)
            if (response.isSuccessful) {
                true
            } else {
                Log.e("UserRepository", "Error deleting user: ${response.errorBody()?.string()}")
                false
            }

        } catch (e: Exception) {
            Log.e("UserRepository", "Exception deleting user: ${e.message}")
            false
        }
    }
}