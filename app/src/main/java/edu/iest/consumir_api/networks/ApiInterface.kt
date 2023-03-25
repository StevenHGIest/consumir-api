package edu.iest.consumir_api.networks

import edu.iest.consumir_api.models.ImagenRandom
import edu.iest.consumir_api.models.ListBreed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("breeds/image/random")
    fun imagenRandom(): Call<ImagenRandom>

    // Cada vez que llamamos si raza = "chihuahua", la url seria breed/chihuhua/imagenes
    @GET("breed/{raza}/images")
    fun listaImagenesDePerrosPorRaza(@Path("raza") raza: String): Call<ListBreed>

}