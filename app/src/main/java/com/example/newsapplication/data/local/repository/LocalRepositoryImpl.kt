package com.example.newsapplication.data.local.repository

import kotlinx.coroutines.flow.MutableStateFlow

class LocalRepositoryImpl: LocalRepository {
    override fun getAll(): MutableStateFlow<String> {
        return MutableStateFlow("Hello World")
    }
}
