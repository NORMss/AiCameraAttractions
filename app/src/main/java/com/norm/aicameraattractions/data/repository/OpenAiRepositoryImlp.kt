package com.norm.aicameraattractions.data.repository

import com.norm.aicameraattractions.data.remote.ChatDto
import com.norm.aicameraattractions.data.remote.ChatRequest
import com.norm.aicameraattractions.data.remote.OpenAiApi
import com.norm.aicameraattractions.domain.repository.OpenAiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OpenAiRepositoryImlp(
    private val openAiApi: OpenAiApi,
) : OpenAiRepository {
    override suspend fun getChatCompletion(
        openAiToken: String,
        chatRequest: ChatRequest
    ): Flow<ChatDto> {
        return callbackFlow {
            openAiApi.getChatCompletion(
                openAiToken,
                chatRequest,
            )
        }
    }
}