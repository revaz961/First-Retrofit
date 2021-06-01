package com.example.firstretrofit.model

data class CountryModel(
    var name: String? = null,
    var capital: String? = null,
    var region: String? = null,
    var subregion: String? = null,
    var nativeName:String? = null,
    var flag:String? = null,
    var languages:List<Language>
)