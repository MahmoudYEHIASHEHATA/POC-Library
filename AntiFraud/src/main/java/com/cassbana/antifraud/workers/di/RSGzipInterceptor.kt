package com.cassbana.antifraud.workers.di

import android.util.Base64
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.Throws
import okhttp3.internal.http.RealResponseBody
import okio.*
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.GZIPOutputStream

const val GZIP_TAG = "GZIP"

class GzipInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        if (originalRequest.body == null || originalRequest.header("Content-Encoding") != null) {
            return chain.proceed(originalRequest)
        }

        Timber.tag(GZIP_TAG).d(bodyToString(chain.request().body!!))
        Timber.tag(GZIP_TAG).d(chain.request().body!!.contentLength().toString())
        val compressedRequest = originalRequest.newBuilder()
            .header("Content-Encoding", "gzip")
            .method(
                originalRequest.method,
                gzip(originalRequest.body)
            )
            .build()
        return chain.proceed(compressedRequest)
    }

    private fun gzip(body: RequestBody?): RequestBody {
        val requestBodyStr = bodyToString(body!!)
        val os = ByteArrayOutputStream(requestBodyStr.length)
        val gos = GZIPOutputStream(os)
        gos.write(requestBodyStr.toByteArray())
        os.close()
        gos.close()
        val compressedStr = Base64.encodeToString(os.toByteArray(), Base64.DEFAULT)
        return compressedStr.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    // copied from okhttp3.internal.http.HttpEngine (because is private)
    @Throws(IOException::class)
    private fun unzip(response: Response): Response {
        if (response.body == null) {
            return response
        }

        //check if we have gzip response
        val contentEncoding = response.headers["Content-Encoding"]

        //this is used to decompress gzipped responses
        return if (contentEncoding != null && contentEncoding == "gzip") {
            val contentLength = response.body!!.contentLength()
            val responseBody = GzipSource(response.body!!.source())
            val strippedHeaders = response.headers.newBuilder().build()
            response.newBuilder().headers(strippedHeaders)
                .body(
                    RealResponseBody(
                        response.body!!.contentType().toString(),
                        contentLength,
                        responseBody.buffer()
                    )
                )
                .build()
        } else {
            response
        }
    }

    private fun bodyToString(request: RequestBody): String {
        return try {
            val buffer = Buffer()
            request.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }
}
