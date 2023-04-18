package edu.iest.consumir_api

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import edu.iest.consumir_api.models.ListBreed
import edu.iest.consumir_api.networks.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var imageRoute: String? = null
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

        var adapter = ArrayAdapter.createFromResource(this, R.array.breeds, android.R.layout.simple_spinner_item)
        spBreed?.adapter = adapter
        spBreed?.onItemSelectedListener = this

        }

    override fun onItemSelected(vistaPadre: AdapterView<*>?, vistaRow: View?, posicion: Int, id: Long) {
        val a = vistaPadre?.getItemAtPosition(posicion).toString()
        traerIamgenPorTipo(a)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun traerIamgenPorTipo(tipo: String) {

        val apiCall = API().crearServicioAPI()
        apiCall.listaImagenesDePerrosPorRaza(tipo).enqueue(object: Callback<ListBreed> {
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
                ivImagen = findViewById(R.id.ivImagen2)
                
                response.body()?.status
            }

            override fun onFailure(call: Call<ListBreed>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}