package com.cassbana.antifraud.workers.di

import android.os.Build
import com.cassbana.antifraud.data.source.local.RSUserDataSource
import com.cassbana.antifraud.data.source.prefs.preferencesGateway
import com.cassbana.antifraud.RSConstants
import com.cassbana.antifraud.RSDomain
import com.cassbana.antifraud.utils.RSUtils
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


const val LOGGING_INTERCEPTOR = "logging-interceptor"
const val NETWORK_INTERCEPTOR = "network-interceptor"
const val ONLINE_CACHE_INTERCEPTOR = "online-cache-interceptor"
const val OFFLINE_CACHE_INTERCEPTOR = "offline-cache-interceptor"
const val CACHE_INTERCEPTOR = "cache-interceptor"
const val LANGUAGE_INTERCEPTOR = "language-interceptor"
const val GZIP_INTERCEPTOR = "GZIP_interceptor"
const val DEFAULT_OKHTTP = "DEFAULT-OKHTTP"
const val GZIP_OKHTTP = "GZIP-OKHTTP"
const val CALL_ADAPTER = "call-adapter"
const val DEFAULT_RETROFIT = "DEFAULT_RETROFIT"
const val FRAUD_RETROFIT = "FRAUD_RETROFIT"
const val OCR_RETROFIT = "OCR_RETROFIT"

fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor { message -> Timber.e(message) };
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideNetworkInterceptor(): Interceptor {

    return Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                request.addHeader("Authorization", RSUserDataSource.getToken())
            }
            job.join()
        }
        chain.proceed(request.build())
    }
}

fun provideLanguageInterceptor(): Interceptor {
    return Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                val cachedLocale = preferencesGateway.load(
                    RSConstants.APP_LANGUAGE,
                    RSConstants.DEFAULT_LANGUAGE
                )

                request.addHeader("Accept-Language", cachedLocale)
                request.addHeader("Content-Type", RSConstants.CONTENT_TYPE)
                request.addHeader("Accept", RSConstants.CONTENT_TYPE)
            }
            job.join()
        }
        chain.proceed(request.build())
    }
}

fun provideOnlineCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
        val request = chain.request()
        val originalResponse = chain.proceed(request)
        val cacheControl = originalResponse.header("Cache-Control")
        if ((cacheControl == null ||
                    cacheControl.contains("no-store") ||
                    cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") ||
                    cacheControl.contains("max-age=0"))
        ) {
            originalResponse
                .newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 0)
                .build()
        } else
            originalResponse
    }
}

fun provideOfflineCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
        var request = chain.request()
        try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            request = request.newBuilder()
                .removeHeader("Pragma")
                .header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                )
                .build()

            chain.proceed(request)
        }
    }
}

fun provideGZIPInterceptor() : Interceptor = GzipInterceptor()

fun provideCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        if (RSUtils.isConnectingToInternet(RSDomain.application)) {
            val maxAge = 60
            originalResponse
                .newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 7
            originalResponse
                .newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }
}

private fun provideChuckerInterceptor(): ChuckerInterceptor {
    val chuckerCollector = ChuckerCollector(
        context = RSDomain.application,
        // Toggles visibility of the push notification
        showNotification = true,
        // Allows to customize the retention period of collected data
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

// Create the Interceptor
   return ChuckerInterceptor.Builder(RSDomain.application.baseContext)
        // The previously created Collector
        .collector(chuckerCollector)
        // The max body content length in bytes, after this responses will be truncated.
        .maxContentLength(250_000L)
        // Read the whole response body even when the client does not consume the response completely.
        // This is useful in case of parsing errors or when the response body
        // is closed before being read like in Retrofit with Void and Unit types.
        .alwaysReadResponseBody(true)
        .build()
}

fun provideOkHttpClient(
    cache: Cache?,
    httpLoggingInterceptor: HttpLoggingInterceptor,
    networkInterceptor: Interceptor,
    languageInterceptor: Interceptor,
    onlineCacheInterceptor: Interceptor,
    offlineCacheInterceptor: Interceptor,
    cacheInterceptor: Interceptor,
    gzipInterceptor: Interceptor?
): OkHttpClient {

    val trustAllCerts: Array<TrustManager> = arrayOf(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

        }
    )
    val sslContext: SSLContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAllCerts, SecureRandom())

    return OkHttpClient().newBuilder().apply {
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            addInterceptor(languageInterceptor)
            addInterceptor(networkInterceptor)
            gzipInterceptor?.let { addInterceptor(it) }
            addInterceptor(httpLoggingInterceptor)
//        addNetworkInterceptor(cacheInterceptor)
            addNetworkInterceptor(onlineCacheInterceptor)
            addInterceptor(offlineCacheInterceptor)
            addInterceptor(provideChuckerInterceptor())
            cache(cache)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            hostnameVerifier { _, _ -> true }
    }.build()
}

fun provideRetrofit(
    url: String,
    okHttpClient: OkHttpClient,
    callAdapterFactory: CallAdapter.Factory,
    converterFactory: Converter.Factory
): Retrofit {
    return Retrofit
        .Builder().apply {
            client(okHttpClient)
            addConverterFactory(converterFactory)
            baseUrl(url)
            addCallAdapterFactory(callAdapterFactory)
        }.build()
}


fun provideJsonConverterFactory(gson: Gson): Converter.Factory {
    return GsonConverterFactory.create(gson);
}

fun provideCallAdapter(): CallAdapter.Factory {
    return RxJava2CallAdapterFactory.create();
}

fun provideCache(): Cache {
    return Cache(File(RSDomain.application.cacheDir, "Responses"), (10 * 1000 * 1000).toLong())
}

val networkModule = module {
    single(named(LOGGING_INTERCEPTOR)) { provideHTTPLoggingInterceptor() }
    single(named(NETWORK_INTERCEPTOR)) { provideNetworkInterceptor() }
    single(named(ONLINE_CACHE_INTERCEPTOR)) { provideOnlineCacheInterceptor() }
    single(named(OFFLINE_CACHE_INTERCEPTOR)) { provideOfflineCacheInterceptor() }
    single(named(CACHE_INTERCEPTOR)) { provideCacheInterceptor() }
    single(named(LANGUAGE_INTERCEPTOR)) { provideLanguageInterceptor() }
    single { provideCache() }
    single { provideJsonConverterFactory(get()) }
    single(named(CALL_ADAPTER)) { provideCallAdapter() }
    single (named(GZIP_INTERCEPTOR)) { provideGZIPInterceptor()}
    single {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }
    single (named(DEFAULT_OKHTTP)){
        provideOkHttpClient(
            cache = null,
            httpLoggingInterceptor = get(named(LOGGING_INTERCEPTOR)),
            networkInterceptor = get(named(NETWORK_INTERCEPTOR)),
            languageInterceptor = get(named(LANGUAGE_INTERCEPTOR)),
            onlineCacheInterceptor = get(named(ONLINE_CACHE_INTERCEPTOR)),
            offlineCacheInterceptor = get(named(OFFLINE_CACHE_INTERCEPTOR)),
            cacheInterceptor = get(named(CACHE_INTERCEPTOR)),
            gzipInterceptor = null
        )
    }
    single (named(GZIP_OKHTTP)){
        provideOkHttpClient(
            get(),
            get(named(LOGGING_INTERCEPTOR)),
            get(named(NETWORK_INTERCEPTOR)),
            get(named(LANGUAGE_INTERCEPTOR)),
            get(named(ONLINE_CACHE_INTERCEPTOR)),
            get(named(OFFLINE_CACHE_INTERCEPTOR)),
            get(named(CACHE_INTERCEPTOR)),
            get(named(GZIP_INTERCEPTOR))
        )
    }

    single { provideRetrofit("https://cassbana-core-api-prod.azure-api.net/api/", get(named(DEFAULT_OKHTTP)), get(named(CALL_ADAPTER)), get()) }

    single(named(DEFAULT_RETROFIT)) {
        provideRetrofit("https://cassbana-core-api-prod.azure-api.net/api/", get(named(DEFAULT_OKHTTP)), get(named(CALL_ADAPTER)), get())
    }
//    single { provideRetrofit(BuildConfig, get(named(DEFAULT_OKHTTP)), get(named(CALL_ADAPTER)), get()) }
//
//    single(named(DEFAULT_RETROFIT)) {
//        provideRetrofit(BuildConfig., get(named(DEFAULT_OKHTTP)), get(named(CALL_ADAPTER)), get())
//    }
//    single(named(FRAUD_RETROFIT)) {
//        provideRetrofit(BuildConfig.FRAUD_SERVER_URL,get(named(GZIP_OKHTTP)), get(named(CALL_ADAPTER)), get())
//    }
//    single(named(OCR_RETROFIT)) {
//        provideRetrofit(BuildConfig.OCR_SERVER_URL, get(named(DEFAULT_OKHTTP)), get(named(CALL_ADAPTER)), get())
//    }
}