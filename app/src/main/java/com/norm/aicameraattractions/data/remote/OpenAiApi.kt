package com.norm.aicameraattractions.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiApi {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") openAiToken: String,
        @Body chatRequest: ChatRequest
    ): Call<ChatDto>

    companion object {
        const val BASE_URL = "https://api.openai.com/"
    }
}