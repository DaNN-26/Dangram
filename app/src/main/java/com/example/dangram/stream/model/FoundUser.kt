package com.example.dangram.stream.model

import kotlinx.serialization.Serializable

@Serializable
data class FoundUser(
    val id: String,
    val email: String,
    val avatar: String
)
