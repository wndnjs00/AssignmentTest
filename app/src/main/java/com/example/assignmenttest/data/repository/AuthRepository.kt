package com.example.assignmenttest.data.repository


interface AuthRepository {
    suspend fun signUp(email: String, password: String, nickName: String) : Result<Unit>
    suspend fun login(email: String, password: String) : Result<Unit>
    suspend fun logout() : Result<Unit>
    suspend fun deleteAccount() : Result<Unit>
}