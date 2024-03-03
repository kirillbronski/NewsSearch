package com.kbcoding.newssearch.news_data.data

sealed class RequestResult<E>(internal val data: E? = null) {
    class InProgress<E>(data: E? = null) : RequestResult<E>(data)
    class Success<E>(data: E) : RequestResult<E>(data)
    class Error<E> : RequestResult<E>()
}

internal fun <T : Any> RequestResult<T?>.requireData(): T {
    return checkNotNull(data)
}

internal fun <I, O> RequestResult<I>.map(mapper: (I?) -> O): RequestResult<O> {
    val outData = mapper(data)
    return when (this) {
        is RequestResult.Success -> RequestResult.Success(outData)
        is RequestResult.Error -> RequestResult.Error()
        is RequestResult.InProgress -> RequestResult.InProgress(outData)
    }
}

internal fun <T> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success(getOrThrow())
        isFailure -> RequestResult.Error()
        else -> error("Unknown branch")
    }
}

