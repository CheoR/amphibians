package com.example.amphibians.data

import com.example.amphibians.model.AmphibianPhoto
import com.example.amphibians.network.AmphibianAPIService

interface AmphibianPhotosRepository {
    /**
     * fetches list of AmphibianPhoto from marsAPI
     */
    suspend fun getAmphibianPhotos(): List<AmphibianPhoto>
}

class NetworkAmphibianPhotosRepository(
    private val amphibianApiService: AmphibianAPIService
): AmphibianPhotosRepository {
    override suspend fun getAmphibianPhotos(): List<AmphibianPhoto>  = amphibianApiService.getPhotos()
}
