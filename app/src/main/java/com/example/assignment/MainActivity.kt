package com.example.assignment

import RandomStringViewModel
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: RandomStringViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomStringApp(viewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RandomStringApp(viewModel: RandomStringViewModel = viewModel()) {
    var maxLength by remember { mutableStateOf("") }
    val stringList by viewModel.allStrings.collectAsState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Random String Generator") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = maxLength,
                onValueChange = { maxLength = it },
                label = { Text("Enter Max String") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    val length = maxLength.toIntOrNull() ?: 10
                    Log.e("TAG", "onClick: Fetch--" + length)
                    viewModel.insert(RandomStringFetcher(context).fetchRandomString(length)!!)
                }) { Text("Fetch String") }

                Button(onClick = {
                    Log.e("TAG", "onClick: Clear")
                    viewModel.deleteAll()
                }) { Text("Clear All") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(stringList) { stringItem ->
                    RandomStringItem(stringItem) { viewModel.delete(stringItem) }
                }
            }
        }
    }
}

@Composable
fun RandomStringItem(randomString: RandomString, onDelete: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text("Value: ${randomString.value}")
            Text("Length: ${randomString.length}")
            Text("Created: ${randomString.created}")
            Button(onClick = onDelete) { Text("Delete") }
        }
    }
}


