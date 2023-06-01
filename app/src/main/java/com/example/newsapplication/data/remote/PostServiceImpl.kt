package com.example.newsapplication.data.remote

import com.example.newsapplication.data.remote.dto.SearchResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse


class PostServiceImpl() : PostService {
    private val client = HttpClient(CIO)
    override suspend fun getSearchByQuery(query: String): SearchResult {
        val response: HttpResponse = client.get("https://content.guardianapis.com/search") {
            url {
                parameters.append("q", query)
            }
        }
        return response.body()
    }
}
