package com.example.testapp

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun DeployableText(text: String) {
    var showMore by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Column(modifier = Modifier
            .animateContentSize(animationSpec = tween(100))//Open animation
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null) {
                showMore = !showMore // Change state of showMore
            }) {

            if (showMore) {
                Text(text = text)//Open all text
            } else {
                Text(text = text, maxLines = 3, overflow = TextOverflow.Ellipsis)//Close text
            }
        }
    }
}