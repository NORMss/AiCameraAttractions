package com.norm.aicameraattractions.data.remote

import com.norm.aicameraattractions.domain.model.Message

data class ChatRequest(
    val messages: List<Message>,
    val model: String,
)