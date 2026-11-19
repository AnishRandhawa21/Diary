package com.anish.diary.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anish.diary.viewmodel.DiaryViewModel

@Composable
fun DiaryScreen(
    modifier: Modifier = Modifier,
    viewModel: DiaryViewModel = viewModel()
) {

    val entries by viewModel.entries.collectAsState(
        initial = emptyList()
    )

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Daily Diary",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Enter Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Enter Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    viewModel.addEntry(title, description)
                    title = ""
                    description = ""
                }
            },
            enabled = entries.size < 3,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Entry")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (entries.size >= 3) {
            Text(
                text = "No more entries allowed!",
                color = MaterialTheme.colorScheme.error
            )
        }

        Text(
            text = "My Entries (${entries.size}/3)",
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn {
            items(entries, key = { it.id }) { entry ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = entry.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(text = entry.description)
                    }
                }
            }
        }
    }
}