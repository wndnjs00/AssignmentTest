package com.example.assignmenttest


interface AuthRepository {
    suspend fun signUp(email: String, password: String, nickName: String) : Result<Unit>
    suspend fun login(email: String, password: String) : Result<Unit>
}