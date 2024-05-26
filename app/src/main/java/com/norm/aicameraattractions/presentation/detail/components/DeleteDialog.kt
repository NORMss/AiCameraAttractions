package com.norm.aicameraattractions.presentation.detail.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DeleteDialog(
    onDeleteClick: () -> Unit,
    onShowDialog: (Boolean) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = {
                onDeleteClick()
                onShowDialog(false)
            }
            ) {
                Text(
                    text = "Delete"
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    onShowDialog(false)
                }
            ) {
                Text(
                    text = "Cancel"
                )
            }
        },
        title = {
            Text(
                text = "Delete photo"
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete this photo?"
            )
        }
    )
}

@Preview
@Composable
fun PreviewDeleteDialog() {
    DeleteDialog(
        {},
        {},
    )
}