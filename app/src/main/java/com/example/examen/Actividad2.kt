package com.example.examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class Actividad2 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var volver: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad2)

        recyclerView = findViewById(R.id.recyclerView)
        volver = findViewById(R.id.volver)
        val listaPokemons = intent.getSerializableExtra("pokemonList") as? ArrayList<Actividad1.Pokemon>

        if (listaPokemons != null && listaPokemons.isNotEmpty()) {
            // Si la lista no está vacía, configura el RecyclerView con el adaptador
            val adapter = PokemonAdapter(listaPokemons)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            // Si la lista está vacía o es nula, muestra un mensaje indicando la ausencia de Pokémon
            mostrarMensajePokemonVacio()
        }

        // Obtener los Pokémon enviados desde la actividad anterior
        val pokemonList = intent.getSerializableExtra("pokemonList") as ArrayList<Actividad1.Pokemon>

        // Configurar el RecyclerView con un Adapter personalizado para mostrar los Pokémon
        val adapter = PokemonAdapter(pokemonList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Añade un listener al botón de volver para regresar a la actividad anterior
        volver.setOnClickListener {
            onBackPressed()
        }
    }

    private fun mostrarMensajePokemonVacio() {
        val emptyTextView = TextView(this)
        emptyTextView.text = "No hay Pokémon añadidos"
        emptyTextView.gravity = Gravity.CENTER

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        emptyTextView.layoutParams = layoutParams
        setContentView(emptyTextView)
    }

    class PokemonAdapter(private val pokemonList: List<Actividad1.Pokemon>) :
        RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

        class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.pokemonName)
            val trainerTextView: TextView = itemView.findViewById(R.id.trainerName)
            val estaturaTextView: TextView = itemView.findViewById(R.id.pokemonType)
            val fechaAtrapadoTextView: TextView = itemView.findViewById(R.id.pokemonDate)
            val pokemonTypeTextView: TextView = itemView.findViewById(R.id.pokemonType)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_item, parent, false)
            return PokemonViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
            val currentPokemon = pokemonList[position]
            holder.nameTextView.text = currentPokemon.nombre
            holder.trainerTextView.text = currentPokemon.entrenador
            holder.estaturaTextView.text = currentPokemon.estatura.toString()

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentPokemon.fechaAtrapado.time)
            holder.fechaAtrapadoTextView.text = formattedDate

            holder.pokemonTypeTextView.text = currentPokemon.tipo
        }


        override fun getItemCount() = pokemonList.size
    }
}