package com.kbcoding.newssearch.news_data.data

interface MergeStrategy<E> {

    fun merge(right: E, left: E): E
}