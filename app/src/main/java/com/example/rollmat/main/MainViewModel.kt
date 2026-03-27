package com.example.rollmat.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollmat.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(false)
    val state: StateFlow<Boolean> = _state

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            _state.value = profileRepository.getSession() != null
        }
    }

    fun logout() {
        _state.value = false
    }
}