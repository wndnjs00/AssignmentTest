package com.example.assignmenttest


sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    object Logout : AuthUiState()
    object AccountDelete : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}