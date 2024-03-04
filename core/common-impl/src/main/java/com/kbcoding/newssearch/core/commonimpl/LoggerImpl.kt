package com.kbcoding.newssearch.core.commonimpl

import android.util.Log

/**
 * Default implementation of [Logger] which sends all logs to the LogCat.
 */
class Logger {

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun e(tag: String, message: String?, exception: Throwable? = null) {
        Log.e(tag, message, exception)
    }

}