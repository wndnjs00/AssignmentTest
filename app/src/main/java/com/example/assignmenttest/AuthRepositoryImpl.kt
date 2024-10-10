package com.example.assignmenttest

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth
): AuthRepository{

    // 회원가입
    override suspend fun signUp(email: String, password: String, nickName: String): Result<Unit> {
        return try{
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }


    // 로그인
    override suspend fun login(email: String, password: String): Result<Unit> {
        return try{
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }


    override suspend fun logout(): Result<Unit> {
        return try{
            firebaseAuth.signOut()  // 로그아웃
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }


    override suspend fun deleteAccount(): Result<Unit> {
        return try{
            val currentUser = firebaseAuth.currentUser
            currentUser?.delete()?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Result.success(Unit)
                }else{
                    Result.failure(Exception("Account deletion failed"))
                }
            }
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}