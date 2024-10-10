package com.gcyazilim.koincryptocrazy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gcyazilim.koincryptocrazy.core.model.CryptoModel
import com.gcyazilim.koincryptocrazy.core.repository.CryptoDownload
import com.gcyazilim.koincryptocrazy.core.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoViewModel(
    private val cryptoDownloadRepository: CryptoDownload
) : ViewModel() {
    val cryptoList: MutableLiveData<Resource<List<CryptoModel>>> = MutableLiveData()

    private var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoList.value = throwable.localizedMessage?.let { Resource.Error(it) }
    }

    fun getDataFromAPI() {
        cryptoList.value = Resource.Loading()



        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = cryptoDownloadRepository.downloadCryptos()
            withContext(Dispatchers.Main) {
                cryptoList.value = resource
            }
        }
    }
}