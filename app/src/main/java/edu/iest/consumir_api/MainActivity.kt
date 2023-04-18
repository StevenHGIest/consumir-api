package edu.iest.consumir_api

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import edu.iest.consumir_api.models.ImagenRandom
import edu.iest.consumir_api.models.ListBreed
import edu.iest.consumir_api.networks.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private var tvRoute: TextView? = null
    private lateinit var bnChange: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvRoute = findViewById(R.id.tvRoute)

        bnChange = findViewById(R.id.bnChangeA1)
        bnChange?.setOnClickListener {
            Toast.makeText(this, "a", Toast.LENGTH_LONG)
            val i = Intent(this, SecondActivity::class.java)
            startActivity(i)
        }
        traerImagenAleatoria()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_images, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Da funcionalidad a las opciones del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_menu_list_imagenes) {
            Toast.makeText(this, "Option menu 1", Toast.LENGTH_LONG).show()
        }

        val apiCall = API().crearServicioAPI()
        apiCall.listaImagenesDePerrosPorRaza("hound").enqueue(object: Callback<ListBreed> {
            override fun onResponse(call: Call<ListBreed>, response: Response<ListBreed>) {
                // Logica
                val dogs = response.body()?.message // Es un array
                Log.d("Pruebas", "Status de la respuesta es ${response.body()?.status}")

                // Si la operacion so soporta nulos se colapsa
                if (dogs != null) {
                    for (dog in dogs) {
                        Log.d("Pruebas", "Perro es $dog")
                    }
                }

                response.body()?.status
            }

            override fun onFailure(call: Call<ListBreed>, t: Throwable) {
                TODO("Not yet implemented")
            }

        }
    )

        return super.onOptionsItemSelected(item)
    }

    private fun traerImagenAleatoria(){
        val apiCall = API().crearServicioAPI()

        apiCall.imagenRandom().enqueue(object : Callback<ImagenRandom> {
            override fun onResponse(call: Call<ImagenRandom>, response: Response<ImagenRandom>) {
                Log.d("API_PRUEBA ", "status es " + response.body()?.status)
                Log.d("API_PRUEBA ", "message es " + response.body()?.message)
                val ivDog = findViewById<ImageView>(R.id.ivDog)
                for (url_imagen in response.body()?.message!!) {
                    Log.d("API_PRUEBA ", "imagen es " + url_imagen)

                }
                Picasso.get()
                    .load(response.body()?.message!!)
                    .resize(500, 500)
                    .centerCrop()
                    .into(ivDog)
                tvRoute?.text = "${tvRoute?.text}${response.body()?.message!!}"
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