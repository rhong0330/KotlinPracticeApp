package com.example.interviewprep.data.remote

import com.example.interviewprep.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserApi  {
    @GET("users")
    suspend fun getUsers(): List<User>
}