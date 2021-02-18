package com.example.show.ui

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.show.data.entities.Goods
import java.util.*

enum class ScreenName { COUNTER, EDIT, ADD_UPDATE, ORDER }

sealed class Screen(val id : ScreenName, val back : MutableState<Boolean> = mutableStateOf(false)) {
    object Counter : Screen(ScreenName.COUNTER)
    object Edit : Screen(ScreenName.EDIT)
    data class AddOrUpdate(var goods : Goods? = null) : Screen(ScreenName.EDIT)
}


private const val SIS_SCREEN = "sis_screen"

private fun Screen.toBundle() : Bundle {
    return bundleOf(SIS_SCREEN to id.name)
}

private fun Bundle.toScreen() : Screen {
    return when (ScreenName.valueOf(getString(SIS_SCREEN, ScreenName.COUNTER.name))) {
        ScreenName.COUNTER -> Screen.Counter
        else -> Screen.Edit
    }
}

fun <T> SavedStateHandle.getMutableStateOf(
    key: String,
    default: T,
    save: (T) -> Bundle,
    restore: (Bundle) -> T
): MutableState<T> {
    val bundle: Bundle? = get(key)
    val initial = if (bundle == null) { default } else { restore(bundle) }
    val state = mutableStateOf(initial)
    setSavedStateProvider(key) {
        save(state.value)
    }
    return state
}

class NavigationViewModel(savedStateHandle : SavedStateHandle) : ViewModel() {

    var currentScreen : Screen by savedStateHandle.getMutableStateOf<Screen>(
        key = SIS_SCREEN,
        default = Screen.Counter,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set
    private val stack = LinkedList<Screen>().apply {
        offer(currentScreen)
    }
    fun onBack() : Boolean{
        if (stack.size <= 1) {
            return false
        }
        currentScreen = stack.poll()?: Screen.Counter
        return currentScreen.back.value
    }

    fun navigateTo(screen: Screen) {
        currentScreen = screen
        stack.offer(screen)
    }

}