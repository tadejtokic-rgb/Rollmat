package com.example.rollmat.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollmat.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)

    val state: StateFlow<ProfileUiState> = _uiState

    fun loadUserData() {
        viewModelScope.launch {
            val session = profileRepository.getSession()

            if (session == null) {
                _uiState.value = ProfileUiState.SignedOut
            } else {
                val userId = session.userId
                val userData = profileRepository.getProfileData(userId)

                if (userData != null) {
                    _uiState.value = ProfileUiState.SignedIn(userData)
                } else {
                    _uiState.value = ProfileUiState.SignedOut
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            profileRepository.signOut()
            _uiState.value = ProfileUiState.SignedOut
        }
    }
}