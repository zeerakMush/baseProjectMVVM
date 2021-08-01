package com.zk.base_project.network.services

import okhttp3.ResponseBody
import retrofit2.http.GET

interface DummyApiServices {
    @GET("https://api.publicapis.org/entries")
    suspend fun getEntries():ResponseBody

}