package com.example.repolearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.repolearning.ui.BookScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = FakeBookApi()
        val repository = BookRepositoryImpl(api)
        val viewModel = BookViewModel(repository)

        setContent {
            BookScreen(viewModel)
        }
    }
}