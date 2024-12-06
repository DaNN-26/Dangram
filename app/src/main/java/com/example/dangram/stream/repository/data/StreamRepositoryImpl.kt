package com.example.dangram.stream.repository.data

import android.util.Log
import com.example.dangram.stream.repository.domain.StreamRepository
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryUsersRequest
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StreamRepositoryImpl @Inject constructor(
    private val chatClient: ChatClient
) : StreamRepository {
    override suspend fun findUserByEmail(email: String): User? =
        withContext(Dispatchers.IO) {
            val filter = Filters.autocomplete("name", email)
            val request = QueryUsersRequest(
                filter = filter,
                offset = 0,
                limit = 10
            )
            val result = chatClient.queryUsers(request).await()
            val users = result.getOrNull()
            if(users.isNullOrEmpty()) {
                Log.d("StreamRepositoryImpl", "No users found")
                return@withContext null
            } else {
                Log.d("StreamRepositoryImpl", "First user ${users.first()}")
                return@withContext users.first()
            }
        }

    override suspend fun createChannel(userId: String, currentUserId: String): Channel =
        withContext(Dispatchers.IO) {
            val clientChannel =
                chatClient.channel(channelType = "messaging", channelId = "")
            val channel =
                clientChannel.create(
                    memberIds = listOf(userId, currentUserId),
                    extraData = emptyMap()
                ).await()
            return@withContext channel.getOrNull() ?: throw Exception("Channel not created")
        }
}