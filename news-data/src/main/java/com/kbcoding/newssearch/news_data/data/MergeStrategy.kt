package com.kbcoding.newssearch.news_data.data

interface MergeStrategy<E> {

    fun merge(left: E, right: E): E
}