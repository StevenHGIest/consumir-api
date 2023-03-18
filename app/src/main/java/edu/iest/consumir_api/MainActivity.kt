package edu.iest.consumir_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import edu.iest.consumir_api.models.ImagenRandom
import edu.iest.consumir_api.networks.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        traerImagenAleatoria()
    }

    private fun traerImagenAleatoria(){
        val apiCall = API().crearServicioAPI()

        apiCall.imagenRandom().enqueue(object : Callback<ImagenRandom> {
            override fun onResponse(call: Call<ImagenRandom>, response: Response<ImagenRandom>) {
                Log.d("API_PRUEBA ", "status es " + response.body()?.status)
                Log.d("API_PRUEBA ", "message es " + response.body()?.message)
                for (url_imagen in response.body()?.message!!) {
                    Log.d("API_PRUEBA ", "imagen es " + url_imagen)
                }
            }

            // Si manda una peticion en los 400-500 se ejecuta esto
            override fun onFailure(call: Call<ImagenRandom>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "No fue posible conectar a API",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }
}