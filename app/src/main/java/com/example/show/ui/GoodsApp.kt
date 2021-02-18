package com.example.show.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.show.ui.theme.GoodsTheme

@Composable
fun GoodsApp(navigationViewModel: NavigationViewModel) {
    GoodsTheme {
        Crossfade(navigationViewModel.currentScreen) {screen->
            Surface(color = MaterialTheme.colors.background) {
                when(screen.id) {
                    ScreenName.COUNTER -> {
                        CounterScreen(screen,navigationViewModel)
                    }
                    ScreenName.EDIT -> {
                        EditStateScreen(onBreak = {
                            navigationViewModel.onBack()
                        })
                    }
                    ScreenName.ADD_UPDATE -> {
                        AddOrUpdateGoodsScreen(onBreak = {
                            navigationViewModel.onBack()
                        })
                    }
                }
            }
        }
    }
}