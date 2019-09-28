package com.project.layoutindex.services.api

import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.project.layoutindex.BuildConfig
import com.project.layoutindex.services.network.InternetConnection
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Singleton
object ApiClient {

    val baseUrl = BuildConfig.API_BASE_URL

    fun client(con : Application): APIInterface {

        var cacheSize = 10 * 1024 * 1024
        var httpCacheDirectory = File(con.cacheDir, "responses")
        var cache = Cache(httpCacheDirectory, cacheSize.toLong())


        var client = OkHttpClient.Builder().cache(cache).addNetworkInterceptor { chain->
            val response = chain.proceed(chain.request())
            val maxAge = 0 // read from cache for 60 seconds even if there is internet connection
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build() }.addInterceptor { chain->
            var request = chain.request()
            if (!InternetConnection.checkInternetConnection(con)) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request) }.build()


        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(APIInterface::class.java)
    }
}