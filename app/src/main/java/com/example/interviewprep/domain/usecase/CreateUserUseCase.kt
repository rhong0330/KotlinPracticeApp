package com.example.interviewprep.domain.usecase

import com.example.interviewprep.data.model.User
import com.example.interviewprep.data.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(user: User): Boolean = repository.createUser(user)
}