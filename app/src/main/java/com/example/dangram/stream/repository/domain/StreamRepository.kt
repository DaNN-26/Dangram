package com.example.dangram.stream.repository.domain

import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.User

interface StreamRepository {
    suspend fun findUserByEmail(email: String): User?

    suspend fun createChannel(userId: String, currentUserId: String): Channel?
}