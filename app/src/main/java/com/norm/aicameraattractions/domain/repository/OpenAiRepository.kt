package com.norm.aicameraattractions.domain.repository

import com.norm.aicameraattractions.data.remote.ChatDto
import com.norm.aicameraattractions.data.remote.ChatRequest

interface OpenAiRepository {
    suspend fun getChatCompletion(
        chatRequest: ChatRequest,
    ): ChatDto
}