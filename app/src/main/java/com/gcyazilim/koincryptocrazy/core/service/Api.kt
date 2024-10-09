package com.gcyazilim.koincryptocrazy.core.service

import com.gcyazilim.koincryptocrazy.core.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData(): Response<List<CryptoModel>>
}