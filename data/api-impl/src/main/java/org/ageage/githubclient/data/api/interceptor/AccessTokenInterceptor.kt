package org.ageage.githubclient.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class AccessTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = "" // TODO トークンを取得する

        val request = chain.request()
        val newRequest = if (accessToken.isNotBlank()) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}