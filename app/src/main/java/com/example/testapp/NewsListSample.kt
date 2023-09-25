package com.example.testapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewsListSample(items: News) {
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        items.value.forEach {
            item {
                OneEventSample(item = it)
            }
        }
    }
}