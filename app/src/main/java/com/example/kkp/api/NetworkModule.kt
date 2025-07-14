package com.example.kkp.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

object NetworkModule {
    
    // Untuk backend lokal (emulator Android)
    private const val BASE_URL = "https://viacomputertangerang.com/api/"
    // Jika pakai device fisik, ganti dengan IP komputer Anda, misal:
    // private const val BASE_URL = "http://192.168.1.10/api/"
    
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .registerTypeAdapter(BigDecimal::class.java, BigDecimalTypeAdapter())
        .create()
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
            
            // Add auth token if available
            val token = SessionManager.getAuthToken()
            if (!token.isNullOrEmpty()) {
                requestBuilder.header("Authorization", "Bearer $token")
            }
            
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

// Custom type adapter for BigDecimal
class BigDecimalTypeAdapter : com.google.gson.JsonSerializer<BigDecimal>, com.google.gson.JsonDeserializer<BigDecimal> {
    override fun serialize(src: BigDecimal?, typeOfSrc: java.lang.reflect.Type?, context: com.google.gson.JsonSerializationContext?): com.google.gson.JsonElement {
        return com.google.gson.JsonPrimitive(src?.toString())
    }
    
    override fun deserialize(json: com.google.gson.JsonElement?, typeOfT: java.lang.reflect.Type?, context: com.google.gson.JsonDeserializationContext?): BigDecimal {
        return BigDecimal(json?.asString ?: "0")
    }
} 