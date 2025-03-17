package com.example.interviewprep.data.repository

import com.example.interviewprep.data.model.User
import com.example.interviewprep.data.remote.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: UserApi) {
    fun getUsers(): Flow<List<User>> = flow {
        try {
            val users = api.getUsers()
            emit(users)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.catch { emit(emptyList()) }
}