package com.example.interviewprep.domain.usecase

import com.example.interviewprep.data.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(userId: Int): Boolean = repository.deleteUser(userId)
}