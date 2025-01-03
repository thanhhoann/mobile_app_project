package com.mobile_app.project.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    val interactionSource = remember { MutableInteractionSource() }
    var query by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query.text,
            onValueChange = { query = TextFieldValue(it) },
            placeholder = { Text(text = "Search...") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            singleLine = true
        )
        IconButton(
            onClick = {
                // Handle search action
                println("Searching for: ${query.text}")
            },
            interactionSource = interactionSource
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    placeholder: String = "Search"
) {
    OutlinedTextField(
        value = query,
        onValueChange = { onQueryChanged(it) },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}