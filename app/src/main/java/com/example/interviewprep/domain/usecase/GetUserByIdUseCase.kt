package com.example.interviewprep.domain.usecase

import com.example.interviewprep.data.model.User
import com.example.interviewprep.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: Int): Flow<User?> = repository.getUserById(userId)
}