package com.example.show.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<NavigationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContent {
            GoodsApp(viewModel)
        }
    }
    override fun onBackPressed() {
        if (!viewModel.onBack()) {
            super.onBackPressed()
        }
    }
}