package com.example.rollmat.profile.repository

import com.example.rollmat.data.SessionDao
import com.example.rollmat.data.Session
import com.example.rollmat.profile.domain.LoginData
import com.example.rollmat.profile.domain.ProfileData
import com.example.rollmat.profile.domain.RegisterData
import com.example.rollmat.profile.domain.Belt
import com.example.rollmat.profile.domain.BeltColor
import kotlinx.coroutines.flow.firstOrNull

import com.example.rollmat.data.UserDao
import com.example.rollmat.data.User
import java.util.UUID

class ProfileRepositoryImpl(
    private val sessionDao: SessionDao,
    private val userDao: UserDao
) : ProfileRepository {
    override suspend fun getSession(): Session? {
        return sessionDao.getSession()
    }

    override suspend fun saveSession(session: Session) {
        sessionDao.insertSession(session)
    }

    override suspend fun getProfileData(userId: String): ProfileData? {
        return userDao.getUserById(userId)?.profileData
    }

    override suspend fun signOut() {
        sessionDao.clearSessions()
    }

    override suspend fun register(registerData: RegisterData) {
        val userId = UUID.randomUUID().toString()
        val profileData = ProfileData(
            id = userId,
            name = registerData.name,
            lastName = registerData.lastName,
            age = registerData.age,
            belt = Belt(
                color = BeltColor.valueOf(registerData.beltColor.uppercase()),
                stripes = registerData.beltStripes
            )
        )
        val user = User(
            id = userId,
            email = registerData.email,
            password = registerData.password,
            profileData = profileData
        )
        userDao.insertUser(user)
    }

    override suspend fun signIn(loginData: LoginData) {
        val user = userDao.login(loginData.email, loginData.password)
        if (user != null) {
            sessionDao.clearSessions()
            sessionDao.insertSession(Session(userId = user.id))
        }
    }
}