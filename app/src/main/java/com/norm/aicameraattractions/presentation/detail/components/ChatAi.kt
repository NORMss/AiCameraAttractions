package com.norm.aicameraattractions.presentation.detail.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.presentation.large_rounded
import com.norm.aicameraattractions.presentation.medium_alpha
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.smale_border
import com.norm.aicameraattractions.presentation.smale_padding

@Composable
fun ChatAi(
    modifier: Modifier = Modifier,
    text: String = "",
    onSendContent: (String) -> Unit,
) {
    var content by remember {
        mutableStateOf(text)
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
                            .padding(
                                vertical = smale_padding
                            )
                            .border(
                                width = smale_border,
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(large_rounded),
                            )
                    ) {
                        if (content.isEmpty()) {
                            Text(
                                text = "Message",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                    .copy(alpha = medium_alpha)
                            )
                        }
                        innerTextFiled()
                    }
                },
            )
            Spacer(
                modifier = Modifier
                    .height(smale_padding)
            )
            if (content.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSendContent(content)
                    },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Send,
                        contentDescription = "send",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewChatAi() {
    ChatAi(
        modifier = Modifier
            .fillMaxSize()
            .padding(medium_padding),
        text = "Hello, Ai!",
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
        onSendContent = { content ->

        }
    )
}