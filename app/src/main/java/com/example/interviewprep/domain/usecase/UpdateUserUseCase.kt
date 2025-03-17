package com.example.interviewprep.domain.usecase

import com.example.interviewprep.data.model.User
import com.example.interviewprep.data.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    val repository: UserRepository
) {
    suspend fun execute(userId: Int, user: User): Boolean = repository.updateUser(userId, user)
}