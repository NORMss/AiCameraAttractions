package com.norm.aicameraattractions.data.repository

import com.norm.aicameraattractions.data.remote.ChatDto
import com.norm.aicameraattractions.data.remote.ChatRequest
import com.norm.aicameraattractions.data.remote.OpenAiApi
import com.norm.aicameraattractions.domain.repository.OpenAiRepository

class OpenAiRepositoryImlp(
    private val openAiApi: OpenAiApi,
) : OpenAiRepository {
    override suspend fun getChatCompletion(
        chatRequest: ChatRequest
    ): ChatDto {
        return openAiApi.getChatCompletion(
            chatRequest,
        )
    }
}