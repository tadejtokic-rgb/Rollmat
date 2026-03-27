package com.example.rollmat.profile.repository

import com.example.rollmat.data.Session
import com.example.rollmat.profile.domain.LoginData
import com.example.rollmat.profile.domain.ProfileData
import com.example.rollmat.profile.domain.RegisterData

interface ProfileRepository {

    suspend fun getSession(): Session?

    suspend fun saveSession(session: Session)

    suspend fun getProfileData(userId: String): ProfileData?

    suspend fun signOut()

    suspend fun register(registerData: RegisterData)

    suspend fun signIn(loginData: LoginData)
}