package com.example.newsapplication.data.remote

import com.example.newsapplication.data.remote.RemoteConstants.BASE_URL
import com.example.newsapplication.data.remote.RemoteConstants.KEY
import com.example.newsapplication.data.remote.dto.SearchResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class PostServiceImpl() : PostService {
    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(CIO) {
        install(Logging){
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }
            )
        }
    }

    override suspend fun getSearchByQuery(query: String, page: Int): SearchResult {

        val response: SearchResult = client.get(BASE_URL) {
            url {
                parameters.append("q", query.replace(" ","%20"))
                parameters.append("api-key", KEY)
                parameters.append("page", page.toString())
                parameters.append("page-size", "20")
            }
        }.body()
        return response
    }
}
