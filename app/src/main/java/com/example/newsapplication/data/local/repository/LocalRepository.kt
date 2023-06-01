package com.example.newsapplication.data.local.repository

import kotlinx.coroutines.flow.MutableStateFlow

interface LocalRepository {
    fun getAll(): MutableStateFlow<String>
}
