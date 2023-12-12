package com.example.examen

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.io.Serializable
import java.util.*

class Actividad1 : AppCompatActivity() {
    private val pokemonList = ArrayList<Pokemon>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad1)

        var volver = findViewById<ImageView>(R.id.volver)
        var spinnerPokemon = findViewById<Spinner>(R.id.spinnerPokemon)
        var fechaAtrapadoLayout = findViewById<TextInputEditText>(R.id.fechaAtrapadoLayout)
        var insertarBtn = findViewById<ImageView>(R.id.insertar)

        insertarBtn.setOnClickListener {
            val nombreLayout = findViewById<TextInputEditText>(R.id.nombreLayout)
            val nombre = nombreLayout.text.toString()

            val entrenadorLayout = findViewById<TextInputEditText>(R.id.entrenadorLayout)
            val entrenador = entrenadorLayout.text.toString()

            val estaturaLayout = findViewById<TextInputEditText>(R.id.estaturaLayout)
            val estatura = estaturaLayout.text.toString()

            // Realizar las validaciones
            if (!validarNombre(nombre)) {
                nombreLayout.error =
                    "Nombre inválido, debe tener al menos 3 caracteres y empezar con mayúscula"
                return@setOnClickListener
            }

            if (!validarEntrenador(entrenador)) {
                entrenadorLayout.error = "Entrenador inválido, debe tener al menos una vocal"
                return@setOnClickListener
            }

            if (!validarEstatura(estatura)) {
                estaturaLayout.error = "Estatura inválida, debe ser un número entero en centímetros"
                return@setOnClickListener
            }


            val fechaAtrapadoLayout = findViewById<TextInputEditText>(R.id.fechaAtrapadoLayout)
            val fechaSeleccionada = fechaAtrapadoLayout.text.toString().split("/")
            val calendar = Calendar.getInstance()
            calendar.set(
                fechaSeleccionada[2].toInt(),
                fechaSeleccionada[1].toInt() - 1,
                fechaSeleccionada[0].toInt()
            )

            if (!validarFechaSeleccionada(calendar)) {
                fechaAtrapadoLayout.error = "Fecha inválida"
                return@setOnClickListener
            }

            if (validarEntrenador(entrenador) && validarNombre(nombre) && validarEstatura(estatura) && validarFechaSeleccionada(calendar)){
                val pokemon = Pokemon(
                    nombre,
                    entrenador,
                    estatura.toInt(),
                    spinnerPokemon.selectedItem.toString(),
                    calendar
                )
                pokemonList.add(pokemon)
                val intent = Intent(this, Actividad2::class.java)
                intent.putExtra("pokemonList", pokemonList)
                startActivity(intent)
            }
        }

            // Flecha para volver al menú principal
            volver.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            // Spinner con tipos de Pokémon
            val tiposPokemon = arrayOf(
                "Agua",
                "Fuego",
                "Tierra",
                "Eléctrico",
                "Planta",
                "Hielo",
                "Lucha",
                "Psíquico",
                "Roca",
                "Bicho"
            )
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposPokemon)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPokemon.adapter = adapter

            // Fecha usando DatePicker
            fechaAtrapadoLayout.setOnClickListener {
                showDatePicker()
            }
    }
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val fechaSeleccionada = "$dayOfMonth/${monthOfYear + 1}/$year"
                val fechaAtrapadoLayout = findViewById<TextInputEditText>(R.id.fechaAtrapadoLayout)
                fechaAtrapadoLayout.setText(fechaSeleccionada)
            }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    private fun validarNombre(nombre: String): Boolean {
        if(nombre[0].isUpperCase() && nombre.length >= 3) {
            return true
        }
        return false
    }

    private fun validarEntrenador(entrenador: String): Boolean {
        if(entrenador.contains(Regex("[aeiou]")) && entrenador.length <= 25) {
            return true
        }
        return false
    }

    private fun validarEstatura(estatura: String): Boolean {
        if(estatura.toIntOrNull() != null) {
            return true
        }
        return false
    }

    private fun validarFechaSeleccionada(fechaSeleccionada: Calendar): Boolean {
        //capturado en el futuro…)
        val fechaActual = Calendar.getInstance()
        if(fechaSeleccionada.before(fechaActual)) {
            return false
        }
        return true
    }

    class Pokemon(
        var nombre: String,
        var entrenador: String,
        var estatura: Int,
        var tipo: String,
        var fechaAtrapado: Calendar
    ) : Serializable
}
