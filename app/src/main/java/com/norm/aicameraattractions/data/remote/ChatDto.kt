package com.norm.aicameraattractions.data.remote

import com.norm.aicameraattractions.domain.model.Choice
import com.norm.aicameraattractions.domain.model.Usage

data class ChatDto(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val system_fingerprint: String,
    val choices: List<Choice>,
    val usage: Usage
)