package com.norm.aicameraattractions.domain.usecases.openaiusecases

import com.norm.aicameraattractions.data.remote.ChatDto
import com.norm.aicameraattractions.data.remote.ChatRequest
import com.norm.aicameraattractions.domain.repository.OpenAiRepository
import kotlinx.coroutines.flow.Flow

class GetChatCompletion(
    private val openAiRepository: OpenAiRepository,
) {
    suspend operator fun invoke(
        openAiToken: String,
        chatRequest: ChatRequest,
    ): Flow<ChatDto> {
        return openAiRepository.getChatCompletion(
            openAiToken = openAiToken,
            chatRequest = chatRequest,
        )
    }
}