package com.example.firstretrofit.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstretrofit.retrofit.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val countriesLiveData = MutableLiveData<List<CountryModel>>().apply {
        mutableListOf<CountryModel>()
    }

    val _countriesLiveData = countriesLiveData

    private val loadingLiveData = MutableLiveData<Boolean>()
    val _loadingLiveData = loadingLiveData


    fun init(){
        CoroutineScope(Dispatchers.IO).launch {
            getCountries()
        }
    }

    private suspend fun getCountries() {
        _loadingLiveData.postValue(true)
        var result = RetrofitService.retrofitService().getCountry()
        if (result.isSuccessful && result.body() != null) {
            countriesLiveData.postValue(result.body())
        }
        _loadingLiveData.postValue(false)
    }
}