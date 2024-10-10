package com.gcyazilim.koincryptocrazy.core.repository

import com.gcyazilim.koincryptocrazy.core.model.CryptoModel
import com.gcyazilim.koincryptocrazy.core.util.Resource

interface CryptoDownload {
    suspend fun downloadCryptos(): Resource<List<CryptoModel>>
}