package com.example.rollmat.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rollmat.profile.domain.ProfileData

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val email: String,
    val password: String,
    @Embedded(prefix = "profile_") val profileData: ProfileData
)
