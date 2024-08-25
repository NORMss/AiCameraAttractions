package com.norm.aicameraattractions.presentation.detail

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.LandmarkUseCases
import com.norm.aicameraattractions.domain.usecases.openaiusecases.OpenAiUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val landmarkUseCases: LandmarkUseCases,
    private val openAiUseCases: OpenAiUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    fun getLandmark(uri: Uri) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectLandmark = landmarkUseCases.selectLandmark(
                        imagePath = uri,
                    )
                )
            }
        }
    }

    fun deleteLandmark() {
        viewModelScope.launch {
            _state.value.selectLandmark?.let {
                landmarkUseCases.deleteLandmark(it)
            }
        }
    }

    fun getChatCompletion(
        content: String,
    ) {
//        viewModelScope.launch {
//            _state.update {
//                it.copy(
//                    messages = openAiUseCases.getChatCompletion(
//                        openAiToken = OPENAI_API_KEY,
//                        chatRequest = ChatRequest(
//                            messages = listOf(
//                                Message(
//                                    role = "system",
//                                    content = "You are a helpful assistant."
//                                ),
//                                Message(
//                                    role = "user",
//                                    content = content,
//                                )
//                            ),
//                            model = "gpt-4o"
//                        )
//                    )
//                )
//            }
//        }
    }
}