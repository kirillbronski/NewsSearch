package com.kbcoding.newssearch.core.commonimpl

import android.util.Log
import com.kbcoding.newssearch.core.common.utils.Logger
import javax.inject.Inject

/**
 * Default implementation of [LoggerImpl] which sends all logs to the LogCat.
 */
class LoggerImpl @Inject constructor() : Logger {

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String?, exception: Throwable?) {
        Log.e(tag, message, exception)
    }

}