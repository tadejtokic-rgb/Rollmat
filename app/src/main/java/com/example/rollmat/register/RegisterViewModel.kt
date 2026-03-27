package com.example.rollmat.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollmat.profile.domain.RegisterData
import com.example.rollmat.profile.repository.ProfileRepository
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RegisterViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var name by mutableStateOf("")
    var lastName by mutableStateOf("")
    var age by mutableStateOf("")
    var beltColor by mutableStateOf("White")
    var beltStripes by mutableStateOf("0")

    var isSuccess by mutableStateOf(false)
        private set

    fun register() {
        if (validate()) {
            viewModelScope.launch {
                val registerData = RegisterData(
                    email = email,
                    password = password,
                    name = name,
                    lastName = lastName,
                    age = age.toIntOrNull() ?: 0,
                    beltColor = beltColor,
                    beltStripes = beltStripes.toIntOrNull() ?: 0
                )
                profileRepository.register(registerData)
                isSuccess = true
            }
        }
    }

    private fun validate(): Boolean {
        return email.isNotBlank() &&
                password.isNotBlank() &&
                name.isNotBlank() &&
                lastName.isNotBlank() &&
                age.isNotBlank()
    }
}
