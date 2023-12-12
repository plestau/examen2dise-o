package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actividad1Btn = findViewById<Button>(R.id.btnActividad1)
        var actividad2Btn = findViewById<Button>(R.id.btnActividad2)
        val pokemonList = ArrayList<Actividad1.Pokemon>()

        actividad1Btn.setOnClickListener {
            val intent = Intent(this, Actividad1::class.java)
            startActivity(intent)
        }

        actividad2Btn.setOnClickListener {
            val intent = Intent(this, Actividad2::class.java)
            intent.putExtra("pokemonList", pokemonList as Serializable)
            startActivity(intent)
        }
    }
}