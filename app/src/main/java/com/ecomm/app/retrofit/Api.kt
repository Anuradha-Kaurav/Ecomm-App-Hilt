package com.ecomm.app.retrofit

import com.ecomm.app.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}