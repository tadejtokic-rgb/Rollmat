package com.example.rollmat.profile.ui

import com.example.rollmat.profile.domain.ProfileData

sealed interface ProfileUiState {

    data object Loading : ProfileUiState

    data object SignedOut : ProfileUiState

    data class SignedIn(
        val profileData: ProfileData
    ) : ProfileUiState
}