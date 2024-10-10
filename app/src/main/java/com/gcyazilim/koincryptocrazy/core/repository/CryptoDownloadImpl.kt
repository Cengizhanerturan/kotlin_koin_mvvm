package com.gcyazilim.koincryptocrazy.core.repository

import com.gcyazilim.koincryptocrazy.core.model.CryptoModel
import com.gcyazilim.koincryptocrazy.core.service.Api
import com.gcyazilim.koincryptocrazy.core.util.Resource

class CryptoDownloadImpl(
    private val api: Api
) : CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {
        return try {
            val response = api.getData()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Error!")
            } else {
                Resource.Error("Error!")
            }
        } catch (e: Exception) {
            Resource.Error("Error! ${e.localizedMessage}")
        }
    }
}