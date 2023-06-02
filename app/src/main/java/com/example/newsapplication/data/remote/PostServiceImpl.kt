package com.example.newsapplication.data.remote

import com.example.newsapplication.data.remote.RemoteConstants.API_KEY_PARAMETER_NAME
import com.example.newsapplication.data.remote.RemoteConstants.BASE_URL_ENDPOINT
import com.example.newsapplication.data.remote.RemoteConstants.KEY
import com.example.newsapplication.data.remote.RemoteConstants.NEW_VALUE_SPACE
import com.example.newsapplication.data.remote.RemoteConstants.PAGE_PARAMETER_NAME
import com.example.newsapplication.data.remote.RemoteConstants.PAGE_SIZE_PARAMETER_NAME
import com.example.newsapplication.data.remote.RemoteConstants.PAGE_SIZE_VALUE
import com.example.newsapplication.data.remote.RemoteConstants.QUERY_PARAMETER_NAME
import com.example.newsapplication.data.remote.RemoteConstants.SECTION_FILTER_NAME
import com.example.newsapplication.data.remote.RemoteConstants.SPACELINE
import com.example.newsapplication.data.remote.RemoteConstants.TYPE_FILTER_NAME
import com.example.newsapplication.data.remote.models.SearchResult
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
        install(Logging) {
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

    override suspend fun getSearchByQuery(
        query: String,
        page: Int,
        sectionFilter: String,
        typeFilter: String
    ): SearchResult {

        val response: SearchResult = client.get(BASE_URL_ENDPOINT) {
            url {
                parameters.append(QUERY_PARAMETER_NAME, query.replace(SPACELINE, NEW_VALUE_SPACE))
                parameters.append(API_KEY_PARAMETER_NAME, KEY)
                parameters.append(PAGE_PARAMETER_NAME, page.toString())
                parameters.append(PAGE_SIZE_PARAMETER_NAME, PAGE_SIZE_VALUE)
                if (sectionFilter.isNotEmpty()) parameters.append(SECTION_FILTER_NAME, sectionFilter)
                if (typeFilter.isNotEmpty()) parameters.append(TYPE_FILTER_NAME, typeFilter)
            }
        }.body()
        return response
    }
}
