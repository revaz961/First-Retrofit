package com.example.firstretrofit.retrofit

import com.example.firstretrofit.model.CountryModel
import retrofit2.Response
import retrofit2.http.GET

interface CountryRepository {
    @GET("/rest/v2/all")
    suspend fun getCountry(): Response<List<CountryModel>>
}