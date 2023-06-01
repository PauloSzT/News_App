package com.example.newsapplication.ui.utils

interface ToUiMapper<T : Any> {
    fun mapToUiNews(id: List<String>): T
}

interface ToDataBaseMapper<T : Any> {
    fun mapToDataBaseModel(): T
}

interface ToUiModelIntegrationMapper<T : Any> {
    fun mapToUiModelIntegration(): T
}