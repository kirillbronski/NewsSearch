package com.kbcoding.newssearch.news_data.data

internal class RequestResponseMergeStrategyImpl<T : Any> : MergeStrategy<RequestResult<T>> {
    override fun merge(
        right: RequestResult<T>,
        left: RequestResult<T>,
    ): RequestResult<T> {
        val cached = right
        val remote = left

        return when {
            cached is RequestResult.InProgress && remote is RequestResult.InProgress -> {
                merge(cached, remote)
            }

            cached is RequestResult.InProgress && remote is RequestResult.Success -> {
                merge(cached, remote)
            }

            cached is RequestResult.InProgress && remote is RequestResult.Error -> {
                merge(cached, remote)
            }

            cached is RequestResult.Success && remote is RequestResult.Success -> {
                merge(cached, remote)
            }

            cached is RequestResult.Success && remote is RequestResult.InProgress -> {
                merge(cached, remote)
            }

            cached is RequestResult.Success && remote is RequestResult.Error -> {
                merge(cached, remote)
            }

            cached is RequestResult.Error && remote is RequestResult.Error -> {
                merge(cached, remote)
            }

            cached is RequestResult.Error && remote is RequestResult.Success -> {
                merge(cached, remote)
            }

            cached is RequestResult.Error && remote is RequestResult.InProgress -> {
                merge(cached, remote)
            }

            else -> error("UnknownMergeStrategyException")
        }
    }

    private fun merge(
        cached: RequestResult.InProgress<T>,
        remote: RequestResult.InProgress<T>,
    ): RequestResult<T> {
        return when {
            remote.data != null -> RequestResult.InProgress(remote.data)
            else -> RequestResult.InProgress(cached.data)
        }
    }

    private fun merge(
        cached: RequestResult.InProgress<T>,
        remote: RequestResult.Success<T>,
    ): RequestResult<T> {
        return RequestResult.InProgress(cached.data)
    }

    private fun merge(
        cached: RequestResult.InProgress<T>,
        remote: RequestResult.Error<T>,
    ): RequestResult<T> {
        return RequestResult.Error(data = cached.data, error = remote.error)
    }

    private fun merge(
        cached: RequestResult.Success<T>,
        remote: RequestResult.Success<T>,
    ): RequestResult<T> {
        return RequestResult.Success(cached.data)
    }

    private fun merge(
        cached: RequestResult.Success<T>,
        remote: RequestResult.InProgress<T>,
    ): RequestResult<T> {
        return RequestResult.InProgress(remote.data)
    }

    private fun merge(
        cached: RequestResult.Success<T>,
        remote: RequestResult.Error<T>,
    ): RequestResult<T> {
        return RequestResult.Error(data = cached.data, error = remote.error)
    }

    private fun merge(
        cached: RequestResult.Error<T>,
        remote: RequestResult.Error<T>,
    ): RequestResult<T> {
        return when {
            remote.error != null -> RequestResult.Error(error = remote.error)
            else -> RequestResult.Error(error = cached.error)
        }
    }

    private fun merge(
        cached: RequestResult.Error<T>,
        remote: RequestResult.Success<T>,
    ): RequestResult<T> {
        return RequestResult.Error(data = remote.data, error = cached.error)
    }

    private fun merge(
        cached: RequestResult.Error<T>,
        remote: RequestResult.InProgress<T>,
    ): RequestResult<T> {
        return RequestResult.Error(data = remote.data, error = cached.error)
    }
}