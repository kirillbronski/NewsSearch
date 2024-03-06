package com.kbcoding.newssearch.core.common.utils

interface Logger {

    fun d(tag: String, message: String)

    fun e(tag: String, message: String?, exception: Throwable? = null)

}