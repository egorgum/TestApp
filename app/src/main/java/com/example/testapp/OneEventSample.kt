package com.example.testapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OneEventSample(item: Event) {
    Card(
        Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)

    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) { // 3

                Text(
                    text = item.label,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                )

                Image(painter = painterResource(id = item.img), contentDescription = null)
                
                DeployableText(text = item.text)
            }
        }
    }
}