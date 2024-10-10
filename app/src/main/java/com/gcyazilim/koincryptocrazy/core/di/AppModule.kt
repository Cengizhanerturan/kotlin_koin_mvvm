package com.gcyazilim.koincryptocrazy.core.di

import com.gcyazilim.koincryptocrazy.core.constant.ApiConstant.Companion.BASE_URL
import com.gcyazilim.koincryptocrazy.core.repository.CryptoDownload
import com.gcyazilim.koincryptocrazy.core.repository.CryptoDownloadImpl
import com.gcyazilim.koincryptocrazy.core.service.Api
import com.gcyazilim.koincryptocrazy.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    //singleton scope
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    single<CryptoDownload> {
        CryptoDownloadImpl(get())
    }

    viewModel {
        CryptoViewModel(get())
    }

}