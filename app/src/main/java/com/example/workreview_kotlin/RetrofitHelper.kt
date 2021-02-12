package com.example.workreview_kotlin

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {
    private val BASE_URL = "http://tks3589.ddns.net:1021/akbtp/api/"
    private var service : Api

    companion object{
        val instance : RetrofitHelper by lazy {
            RetrofitHelper()
        }
    }

    init {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .build()

        service = retrofit.create(Api::class.java)
    }

    suspend fun getMembers(): String {
        return service.getMembers()
    }

    fun getProduct(name:String): Deferred<String> {
        return service.getProduct(name)
    }
}