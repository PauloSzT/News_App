package com.example.newsapplication.ui.utils

interface ToUiMapper<T : Any> {
    fun mapToUiModel(): T
}

interface ToDataBaseMapper<T : Any> {
    fun mapToDataBaseModel(): T
}
