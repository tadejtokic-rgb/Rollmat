package com.example.rollmat.profile.domain

data class RegisterData(
    val email: String,
    val password: String,
    val name: String,
    val lastName: String,
    val age: Int,
    val beltColor: String,
    val beltStripes: Int
)