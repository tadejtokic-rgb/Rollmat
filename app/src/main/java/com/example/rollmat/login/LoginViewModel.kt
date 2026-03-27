package com.example.rollmat.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollmat.profile.domain.LoginData
import com.example.rollmat.profile.repository.ProfileRepository
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isSuccess by mutableStateOf(false)
        private set

    fun login() {
        if (validate()) {
            viewModelScope.launch {
                val loginData = LoginData(email = email, password = password)
                profileRepository.signIn(loginData)
                // Check if session was created successfully
                if (profileRepository.getSession() != null) {
                    isSuccess = true
                }
            }
        }
    }

    private fun validate(): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }
}
