package com.example.rollmat.profile.domain

import androidx.room.Embedded

data class ProfileData(
    val id: String,
    val name: String,
    val lastName: String,
    val age: Int,
    @Embedded val belt: Belt
)