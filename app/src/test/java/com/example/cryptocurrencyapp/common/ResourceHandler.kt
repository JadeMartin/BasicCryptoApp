package com.example.cryptocurrencyapp.common

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.File

class ResourceHandler constructor(
    private val server: MockWebServer
) {
    fun uploadRequest(fileName: String, responseCode: Int) = server.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJsonFile(fileName)))

    private fun getJsonFile(path : String) : String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    fun shutdown() = server.shutdown()
}