package com.example.interviewprep.data.remote

import com.example.interviewprep.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi  {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): Response<User>

    @POST("users")
    suspend fun createUser(@Body user: User): Response<User>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") userId: Int, @Body user: User): Response<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") userId: Int) : Response<Unit>


}