package com.example.show

import android.app.Application
import android.content.Context

class ShowApplication : Application() {

    init {
        instance = requireNotNull(this)
    }

    companion object {
        private lateinit var instance: ShowApplication

        fun applicationContext(): Context {
            return instance
        }
    }
}