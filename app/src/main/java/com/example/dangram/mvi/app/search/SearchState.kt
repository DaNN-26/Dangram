package com.example.dangram.mvi.app.search

import com.example.dangram.stream.model.FoundUser
import kotlinx.serialization.Serializable

@Serializable
data class SearchState(
    val email: String = "",
    val foundUser: FoundUser? = null,
    val isUserFounded: Boolean = false,
    val isUserNull: Boolean = false,
    val isCurrentUser: Boolean = false
)
