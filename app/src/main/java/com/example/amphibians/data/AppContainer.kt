package com.example.amphibians.data

import com.example.amphibians.network.AmphibianAPIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphibianPhotosRepository: AmphibianPhotosRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl =
        "https://android-kotlin-fun-mars-server.appspot.com"

    /**
     * Use Retrofit builder to build retrofit object using kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: AmphibianAPIService by lazy {
        retrofit.create(AmphibianAPIService::class.java)
    }

    override val amphibianPhotosRepository: AmphibianPhotosRepository by lazy {
        NetworkAmphibianPhotosRepository(retrofitService)
    }

}