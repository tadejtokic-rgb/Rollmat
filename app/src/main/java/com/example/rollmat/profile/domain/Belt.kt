package com.example.rollmat.profile.domain

data class Belt(
    val color: BeltColor,
    val stripes: Int = 0
)

enum class BeltColor {
    BLACK,
    BROWN,
    PURPLE,
    BLUE,
    WHITE
}