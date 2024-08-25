package com.norm.aicameraattractions.domain.repository

import com.norm.aicameraattractions.data.remote.ChatDto
import com.norm.aicameraattractions.data.remote.ChatRequest
import kotlinx.coroutines.flow.Flow

interface OpenAiRepository {
    suspend fun getChatCompletion(
        openAiToken: String,
        chatRequest: ChatRequest,
    ): Flow<ChatDto>
}