package com.norm.aicameraattractions.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.domain.model.Message
import com.norm.aicameraattractions.presentation.large_rounded
import com.norm.aicameraattractions.presentation.max_width_messagebox
import com.norm.aicameraattractions.presentation.medium_alpha
import com.norm.aicameraattractions.presentation.medium_border
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.medium_rounded
import com.norm.aicameraattractions.presentation.small_padding
import com.norm.aicameraattractions.presentation.very_small_padding

@Composable
fun ChatAi(
    modifier: Modifier = Modifier,
    text: String = "",
    messages: List<Message>,
    isSending: Boolean,
    onSendContent: (String) -> Unit,
) {
    var content by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(messages) { message ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    MessageBox(
                        message = message.content,
                        modifier = Modifier
                            .align(
                                if (message.content.isNotEmpty()) Alignment.End else Alignment.Start
                            )
                    )
                    Spacer(
                        modifier = Modifier
                            .height(medium_padding)
                    )
                }

            }
        }
        Spacer(
            modifier = Modifier
                .height(medium_padding)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = content,
                onValueChange = {
                    content = it
                },
                modifier = Modifier
                    .weight(1f),
                decorationBox = { innerTextFiled ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                TextFieldDefaults.MinHeight
                            )
                            .clip(
                                RoundedCornerShape(large_rounded),
                            )
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                            .border(
                                width = medium_border,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(large_rounded),
                            )
                            .padding(
                                horizontal = medium_padding,
                            ),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (content.isEmpty()) {
                            Text(
                                text = "Message",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                    .copy(alpha = medium_alpha)
                            )
                        }
                        if (isSending) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        }
                        innerTextFiled()
                    }
                },
            )
            if (content.isNotEmpty() && !isSending) {
                Spacer(
                    modifier = Modifier
                        .width(small_padding)
                )
                IconButton(
                    onClick = {
                        onSendContent(content)
                    },
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Send,
                        contentDescription = "send",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBox(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(medium_rounded)
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .padding(
                small_padding
            )
    ) {
        Text(
            text = "Test User",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(medium_alpha),
        )
        Spacer(
            modifier = Modifier
                .height(very_small_padding)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .widthIn(max = max_width_messagebox)
        )
    }
}

@Preview
@Composable
fun PreviewMessageBox() {
    MessageBox(
        message = "Hello, Chat!",
    )
}

@Preview
@Composable
private fun PreviewChatAi() {
    ChatAi(
        modifier = Modifier
            .fillMaxSize()
            .padding(medium_padding),
        text = "Hello, Ai!",
        messages = messagesTest,
        isSending = false,
        onSendContent = { content ->

        }
    )
}

@Preview
@Composable
private fun PreviewChatAiContentEmpty() {
    ChatAi(
        modifier = Modifier
            .fillMaxSize()
            .padding(medium_padding),
        messages = messagesTest,
        isSending = false,
        onSendContent = { content ->

        }
    )
}

@Preview
@Composable
private fun PreviewChatAiContentEmptySending() {
    ChatAi(
        modifier = Modifier
            .fillMaxSize()
            .padding(medium_padding),
        messages = messagesTest,
        isSending = true,
        onSendContent = { content ->

        }
    )
}

@Preview
@Composable
private fun PreviewChatAiText() {
    ChatAi(
        modifier = Modifier
            .fillMaxSize()
            .padding(medium_padding),
        text = "Hello, Ai!,Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!\nHello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!\nHello, Ai!Hello, Ai!Hello, Ai!Hello, Ai!\nHello, Ai!Hello, Ai!Hello, Ai!",
        messages = messagesTest,
        isSending = false,
        onSendContent = { content ->

        }
    )
}

val messagesTest = listOf(
    Message(
        role = "User",
        content = "Hello, Android!",
    ),
    Message(
        role = "ChatAi",
        content = "Hello, Chat!",
    ),
    Message(
        role = "User",
        content = "Hello, World!",
    ),
    Message(
        role = "User",
        content = "Hello, Google!",
    ),
)