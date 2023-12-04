package com.example.socketiotest.simpleFeature.presentation

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.socketiotest.simpleFeature.presentation.components.TextAiArtMaster

@Composable
fun SampleScreen() {

    val viewModel: SampleViewModel = hiltViewModel()

    val listMessages = remember {
        mutableStateOf(listOf<String>())
    }

    LaunchedEffect(key1 = null, block = {
        viewModel.uiState.collect() {
            Log.d("rawr","screen $it")
            listMessages.add(it)
        }
    })

    TextAiArtMaster(text = "ExampleFeature")

    LazyColumn() {
        item { 
            Text(text = "Header")
            Button(onClick = {
                viewModel.buttonClicked()
            }) {
                Text(text = "Button")
            }
        }
        items(listMessages.value) {
            Text(text = it)
        }
    }
}

fun MutableState<List<String>>.add(message: String) {
    val newList: MutableList<String> = mutableListOf()
    newList.addAll(this.value)
    newList.add(message)
    this.value = newList
}

