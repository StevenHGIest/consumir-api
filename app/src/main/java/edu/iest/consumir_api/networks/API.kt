package edu.iest.consumir_api.networks

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API {

    // Ponemos la url base
    private val URL_BASE = "https://dog.ceo/api/"

    fun crearServicioAPI(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
                // Aqui se convirte a json
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiInterface = retrofit.create(ApiInterface::class.java)
        return service
    }

}