package com.eck.compose_migration.managers

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

class LogManagerImpl : LogManager {

    private val tag = "ComposeMigration"

    override fun log(message: String?) {
        log(tag, message)
    }

    override fun log(tag: String, message: String?) {
        message?.let {
            Log.d(tag, it)
        }
    }

    override fun logError(throwable: Throwable) {
        Log.e(tag, "Error: ", throwable)
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    override fun logError(tag: String, message: String?) {
        message?.let {
            Log.e(tag, it)
        }
    }
}