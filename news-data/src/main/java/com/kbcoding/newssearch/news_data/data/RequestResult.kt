package com.kbcoding.newssearch.news_data.data

sealed class RequestResult<E>(internal val data: E) {
    class InProgress<E>(data: E) : RequestResult<E>(data)
    class Success<E>(data: E) : RequestResult<E>(data)
    class Error<E>(data: E) : RequestResult<E>(data)
}

internal fun <T : Any> RequestResult<T?>.requireData(): T {
    return checkNotNull(data)
}

internal fun <I, O> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O> {
    val outData = mapper(data)
    return when (this) {
        is RequestResult.Success -> RequestResult.Success(outData)
        is RequestResult.Error -> RequestResult.Error(outData)
        is RequestResult.InProgress -> RequestResult.InProgress(outData)
    }
}

