package com.example.newsapplication.data.remote

import com.example.newsapplication.data.remote.RemoteConstants.KEY
import com.example.newsapplication.data.remote.dto.SearchResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class PostServiceImpl() : PostService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
            )
        }
    }

    override suspend fun getSearchByQuery(query: String): SearchResult {
        val response: SearchResult = client.get("https://content.guardianapis.com/search") {
            url {
                parameters.append("q", query)
                parameters.append("api-key", KEY)
            }
        }.body()
        return response
    }
}
