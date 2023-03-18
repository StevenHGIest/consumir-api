package edu.iest.consumir_api.networks

import edu.iest.consumir_api.models.ImagenRandom
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("breeds/image/random")
    fun imagenRandom(): Call<ImagenRandom>
}