package com.example.capitalesafricakotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_resultados.*


class Resultados : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        var resultado = intent.extras?.get("valoresResultado") as Resultado // Obtiene los datos pasados del mainActivity

        txtIntentos.text = resultado.intentos.toString() // Muestra el valor de la variable intentos
        txtAciertos.text = resultado.aciertos.toString() // Muestra el valor de la variable aciertos
        txtFallos.text = resultado.fallos.toString() // Muestra el valor de la variable fallos

        btnVolver.setOnClickListener { // Acción al pulsar el botón volver
            val intent = Intent(this@Resultados, MainActivity::class.java) // Intent que hace que pasemos de Resultados a mainActivity
            startActivity(intent) // Se inicia la activity
        }
    }
}