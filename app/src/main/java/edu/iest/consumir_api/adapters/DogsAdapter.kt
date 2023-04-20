package edu.iest.consumir_api.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.iest.consumir_api.R
import edu.iest.consumir_api.models.ListBreed

class DogsAdapter(dogs: ArrayList<String>, context: Context):
    RecyclerView.Adapter<DogsAdapter.ContenedorDeVista>() {

    val innerDogs: ArrayList<String> = dogs
    inner class ContenedorDeVista(view: View): RecyclerView.ViewHolder(view) {
            val image: ImageView

            init {
                image = view.findViewById(R.id.ivBreedDog)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContenedorDeVista {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_dog, parent, false)

        return ContenedorDeVista(view)
    }

    override fun onBindViewHolder(holder: ContenedorDeVista, position: Int) {
        val dog = innerDogs[position]
            Picasso.get()
                .load(dog)
                .resize(500, 500)
                .centerCrop()
                .into(holder.image)

    }

    override fun getItemCount(): Int {
        return innerDogs.size
    }
}