package com.example.interviewprep.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewprep.data.model.User
import com.example.interviewprep.domain.usecase.CreateUserUseCase
import com.example.interviewprep.domain.usecase.DeleteUserUseCase
import com.example.interviewprep.domain.usecase.GetUserByIdUseCase
import com.example.interviewprep.domain.usecase.GetUsersUseCase
import com.example.interviewprep.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .onStart { _isLoading.value = true }
                .catch { e ->
                    _errorMessage.value = e.message
                    _isLoading.value = false
                }
                .collect { userList ->
                    _users.value = userList
                    _isLoading.value = false
                }
        }
    }

    fun getUserById(userId: Int): Flow<User?> {
        return getUserByIdUseCase(userId)
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            try {
                val success = withContext(Dispatchers.IO) { createUserUseCase.execute(user) }
                if (success) {
                    _users.value = _users.value.toMutableList().apply { add(user) }
                    //_users.value += user
                } else {
                    _errorMessage.value = "Failed to create user"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun updateUser(userId: Int, user: User) {
        viewModelScope.launch {
            try {
                val success = updateUserUseCase.execute(userId, user)
                if (success) {
                    _users.value = _users.value.map { if (it.id == userId) user else it }
                } else {
                    _errorMessage.value = "Failed to update user"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            try {
                val success = deleteUserUseCase.execute(userId)
                if (success) {
                    _users.value = _users.value.filter { it.id != userId }
                } else {
                    _errorMessage.value = "Failed to delete user"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }

}

