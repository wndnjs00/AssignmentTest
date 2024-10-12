package com.example.assignmenttest

import java.util.regex.Pattern

class Validate {
     fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
     }

    fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,16}$"
        return password.isNotEmpty() && Pattern.matches(passwordPattern, password.trim())
    }

    fun isPasswordCheckValid(password: String, passwordCheck: String): Boolean {
        return passwordCheck.isNotEmpty() && password == passwordCheck
    }

    fun isNicknameValid(nickname: String): Boolean {
        return nickname.isNotEmpty()
    }
}