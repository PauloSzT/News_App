package com.example.newsapplication.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Detail Screen" )
    }
}
