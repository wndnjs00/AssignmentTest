package com.example.assignmenttest.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttest.AuthUiState
import com.example.assignmenttest.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // nickName값 sharedViewModel로 공유하기위해
    private val _nickName = MutableStateFlow<String?>(null)
    val nickName: StateFlow<String?> = _nickName

    val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> = _email



    fun signUp(email: String, password: String, nickName: String){
        viewModelScope.launch{
            val result = authRepository.signUp(email, password, nickName)

            _uiState.value = AuthUiState.Loading
            _uiState.value = if(result.isSuccess){
                AuthUiState.Success
            }else{
                AuthUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }


    fun login(email:String, password: String){
        viewModelScope.launch {
            val result = authRepository.login(email, password)

            _uiState.value = AuthUiState.Loading
            _uiState.value = if(result.isSuccess){
                AuthUiState.Success
            }else{
                AuthUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }


    fun sharedNickName(nickName: String){
        _nickName.value = nickName
    }

    fun sharedEmail(email: String){
        _email.value = email
    }


    fun logout(){
        viewModelScope.launch {
            val result = authRepository.logout()

            _uiState.value = if (result.isSuccess){
                AuthUiState.Logout
            }else{
                AuthUiState.Error(result.exceptionOrNull()?.message ?: "Logout failed")
            }
        }
    }


    fun deleteAccount(){
        viewModelScope.launch {
            val result = authRepository.deleteAccount()

            _uiState.value = if(result.isSuccess){
                AuthUiState.AccountDelete
            }else{
                AuthUiState.Error(result.exceptionOrNull()?.message ?: "Account deletion failed")
            }
        }
    }

}