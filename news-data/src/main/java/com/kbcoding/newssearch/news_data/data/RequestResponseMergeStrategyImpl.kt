package com.kbcoding.newssearch.news_data.data

class RequestResponseMergeStrategyImpl<T>: MergeStrategy<RequestResult<T>> {
    override fun merge(left: RequestResult<T>, right: RequestResult<T>): RequestResult<T> {
        TODO("Not yet implemented")
    }
}