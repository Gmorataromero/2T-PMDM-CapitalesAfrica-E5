package com.example.capitalesafricajava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Resultados extends AppCompatActivity
{
        @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        // Elementos gráficos
        Button btnVolver = (Button) findViewById(R.id.btnVolver);
        TextView txtIntentos = (TextView) findViewById(R.id.txtIntentos);
        TextView txtAciertos = (TextView) findViewById(R.id.txtAciertos);
        TextView txtFallos = (TextView) findViewById(R.id.txtFallos);

        Resultado resultado = (Resultado) getIntent().getExtras().getSerializable("valoresResultado"); // Obtiene los datos de intntos, aciertos y fallos que se le pasan del mainactivity

        txtIntentos.setText(String.valueOf(resultado.getIntentos())); // Muestra el valor de la variable intentos
        txtAciertos.setText(String.valueOf(resultado.getAciertos())); // Muestra el valor de la variable aciertos
        txtFallos.setText(String.valueOf(resultado.getFallos())); // Muestra el valor de la variable fallos

        btnVolver.setOnClickListener(new View.OnClickListener() // Acción al pulsar el botón volver
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Resultados.this, MainActivity.class); // Intent que hace que pasemos de resultados a mainActivity
                startActivity(intent); // Se inicia la activity
            }
        });
    }
}