package com.example.aionlinerecurtement.Response

import android.util.Base64
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReTrofit {
private val AUTH="Basic"+Base64.encodeToString("sss".toByteArray(),Base64.NO_WRAP)
    private val URL="https://wizzie.online/Airecruitment/"
    private val okhttps=okhttp3.OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val build = request.newBuilder()
                .method(request.method(), request.body())
                .addHeader("Authorization", AUTH)
            val requests = build.build()
            chain.proceed(requests)
        }.build()
val instance:Api by lazy {
val retrfoit= Retrofit.Builder()
    .baseUrl(URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttps)
    .build()
    retrfoit.create(Api::class.java)
}
}