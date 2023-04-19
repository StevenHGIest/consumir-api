package edu.iest.consumir_api

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso
import edu.iest.consumir_api.models.ListBreed
import edu.iest.consumir_api.networks.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var ivImagen: ImageView? = null
    private var bnChange: Button? = null
    private var spBreed: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        bnChange = findViewById(R.id.bnChangeA2)
        spBreed = findViewById(R.id.spTipoDePerros)
        bnChange?.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        val adapter = ArrayAdapter.createFromResource(this, R.array.breeds, android.R.layout.simple_spinner_item)
        spBreed?.adapter = adapter
        spBreed?.onItemSelectedListener = this

        }

    // Metodos Spinner
    override fun onItemSelected(vistaPadre: AdapterView<*>?, vistaRow: View?, posicion: Int, id: Long) {
        val a = vistaPadre?.getItemAtPosition(posicion).toString()
        traerIamgenPorTipo(a)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    // Funcion
    fun traerIamgenPorTipo(tipo: String) {

        val apiCall = API().crearServicioAPI()
        apiCall.listaImagenesDePerrosPorRaza(tipo).enqueue(object: Callback<ListBreed> {
            override fun onResponse(call: Call<ListBreed>, response: Response<ListBreed>) {
                // Logica
                val dogs = response.body()?.message // Es un arr

                ivImagen = findViewById(R.id.ivImagen2)
                val selectedImage = dogs?.get(0)
                val preferencias = getSharedPreferences("PERSISTENCIA", MODE_PRIVATE)
                val edit = preferencias.edit()
                edit.putString(tipo, selectedImage)
                edit.apply()
                Picasso.get()
                    .load(selectedImage)
                    .resize(500, 500)
                    .centerCrop()
                    .into(ivImagen)
                
                response.body()?.status
            }

            override fun onFailure(call: Call<ListBreed>, t: Throwable) {
                val preferences = getSharedPreferences("PERSISTENCIA", MODE_PRIVATE)
                val selectedImage = preferences.getString(tipo, "").toString()
                Picasso.get()
                    .load(selectedImage)
                    .resize(500, 500)
                    .centerCrop()
                    .into(ivImagen)
            }

        })
    }
}