package com.example.capitalesafricajava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    // Se declaran los HashMap
    HashMap<String, String> paises = new HashMap<String, String>();
    HashMap<String, String> opciones = new HashMap<String, String>();

    // Se crean dos bases de datos, una para países y otra para opciones
    DbHandler dbHandlerPaises = new DbHandler(MainActivity.this,"capitalesAfricaPaises",1);
    DbHandler dbHandlerOpciones = new DbHandler(MainActivity.this,"capitalesAfricaOpciones",1);
    Random random = new Random(); // Random

    // Elementos gráficos
    ImageButton btnEmpezar;
    Button btnCambiarActividad;
    RadioGroup radioButtonGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    TextView txtPais;
    TextView buttonText;
    TextView textView;

    String paisActual = "";
    int aciertos = 0;
    int fallos = 0;
    int intentos = 0;
    boolean comienzo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarElementosGraficos(); // Método que inicia los elementos gráficos
        iniciarBaseDatos(); // Método que inicia la base de datos

        btnEmpezar.setOnClickListener(new View.OnClickListener() // Acciones cuando se pulsa el botón empezar
        {
            @Override
            public void onClick(View v) // Al hacer clic
            {
                try
                {
                    jugar(); // Método que inicia el juego
                }
                    catch (Exception e)
                    {
                        if(dbHandlerPaises.obtenerTamano() > 0) // Controla que si el usuario no hace ninguna acción antes de pulsar el botón de  mostrar país, el contador siga sumando y continúe el juego mientras hayan países que mostrar
                        {
                            intentos++; // Intentos aumenta su valor en 1
                            fallos++; // Fallos aumenta su valor en 1
                            siguiente(); // Método que controla que el juego continúe
                        }
                    }
            }
        });

        btnCambiarActividad.setOnClickListener(new View.OnClickListener() // Acciones del botón ver resultados
        {
            @Override
            public void onClick(View v)
            {
                Log.v(":::","Has pulsado el boton ver resultados"); // Log que indica que se ha pulsado el botón ver resultados
                Resultado resultado = new Resultado(intentos,aciertos,fallos); // Objeto que pasa valor de intentos, aciertos y fallos
                Intent intent = new Intent(MainActivity.this, Resultados.class); // Intent que hace que pasemos de mainactivity a Resultados
                intent.putExtra("valoresResultado", resultado); // Le pasa el objeto
                startActivity(intent); // Se inicia la activity
            }
        });
    }

    public void jugar() // Método que inicia el juego
    {
        if (!comienzo) // Si comienzo es true
        {
            iniciarJuego(); // Inicia el juego y hace visible el país y los radiobutton con las opciones de capitales
        }
        else // Si comienzo es false
        {
            int eleccion = radioButtonGroup.getCheckedRadioButtonId();
            RadioButton radioElegido = findViewById(eleccion); // Se almacena el radiobutton elegido del radiobuttongroup

            if(radioElegido.getText().equals(dbHandlerPaises.obtenerPaisPorNombre(paisActual).getCapital())) //Comprueba si la capital seleccionada en el radiobutton es la que le corresponde al país
            {
                intentos++; // Aumenta el valor de intentos
                aciertos++; // Aumenta el valor de aciertos
            }
                else
                {
                    intentos++;
                    fallos++;
                }
        }

        radioButtonGroup.clearCheck(); // Limpia la selección del grupo de radiobutton
        siguiente(); // Método que controla que el juego continúe
    }

    public void siguiente() // Método que controla que el juego continúe
    {
        if(dbHandlerPaises.obtenerTamano() <= 1) // Si no hay pasíses en la bbdd dbHandlerPaíses
        {
            btnCambiarActividad.setVisibility(View.VISIBLE); // Hace visible el botón de ver resultados que pasa al otro activitu para ver resultados
            btnEmpezar.setVisibility(View.INVISIBLE); // Hace invisible el botón empezar
            radioButtonGroup.setVisibility(View.INVISIBLE); // Hace invisible el grupo de radiobutton
            txtPais.setVisibility(View.INVISIBLE); // Hace invisible el país
            buttonText.setVisibility(View.INVISIBLE); // Hace invisible el texto de "Pulse para empezar"
            textView.setText("Fin del juego"); // Aparece un mensaje de "Fin del juego"
        }
            else // Si siguien habiendo países en la bbdd dbHandlerPaises
            {
                dbHandlerPaises.eliminarPais(paisActual); // Se elimina el país de la bbdd dbHandlerPaises
                int id = random.nextInt(54 - 1 + 1) + 1; // Id random
                String intentoPais = dbHandlerPaises.obtenerPaisPorID(id).getPais(); //Se obtiene el país con el id random

                while(intentoPais.equals("error")) // Si no hay país con ese id porque ya se haya jugado
                {
                    id = random.nextInt(54 - 1 + 1) + 1; // Vuelve a obtener un número random
                    intentoPais = dbHandlerPaises.obtenerPaisPorID(id).getPais(); // Vuelve a obtener el país con el id random
                }

                paisActual = dbHandlerPaises.obtenerPaisPorID(id).getPais(); // Obtiene un nuevo país
                txtPais.setText(paisActual); // Muestra el nuevo país
                prepararOpciones(); // Método que hace que se muestren 4 opciones de capitales
            }
    }

    public void prepararOpciones()
    {
        radioButton1.setText(dbHandlerOpciones.obtenerPaisPorID(random.nextInt(54 - 1 + 1) + 1).getCapital()); // Muestra una capital random de las capitales almacenadas en dbHandlerOpciones
        radioButton2.setText(dbHandlerOpciones.obtenerPaisPorID(random.nextInt(54 - 1 + 1) + 1).getCapital()); // Muestra una capital random de las capitales almacenadas en dbHandlerOpciones
        radioButton3.setText(dbHandlerOpciones.obtenerPaisPorID(random.nextInt(54 - 1 + 1) + 1).getCapital()); // Muestra una capital random de las capitales almacenadas en dbHandlerOpciones
        radioButton4.setText(dbHandlerOpciones.obtenerPaisPorID(random.nextInt(54 - 1 + 1) + 1).getCapital()); // Muestra una capital random de las capitales almacenadas en dbHandlerOpciones

        String respuestaCorrecta = dbHandlerPaises.obtenerPaisPorNombre(paisActual).getCapital(); // Almacena en una variable la capital correcta del país que se mostrará
        Log.v(":::","Respuesta correcta : " + respuestaCorrecta); // Log con la respuesta correcta

        // Entrará en el método prepararOpciones hasta que los radiobutton no se repitan entre si, no sean nulos, y alguno de ellos contenga la capital correcta del país que se muestra
        if( (!radioButton1.getText().equals(respuestaCorrecta)  && !radioButton2.getText().equals(respuestaCorrecta)  && !radioButton3.getText().equals(respuestaCorrecta)  && !radioButton4.getText().equals(respuestaCorrecta)) || (radioButton1.getText().equals(radioButton2.getText()) || radioButton1.getText().equals(radioButton3.getText()) || radioButton1.getText().equals(radioButton4.getText()) || radioButton2.getText().equals(radioButton3.getText()) || radioButton2.getText().equals(radioButton4.getText()) || radioButton3.getText().equals(radioButton4.getText()) ) || (radioButton1.getText() == null || radioButton2.getText() == null || radioButton3.getText() == null || radioButton4.getText() == null) || (radioButton1.getText() == "null" || radioButton2.getText() == "null" || radioButton3.getText() == "null" || radioButton4.getText() == "null") )
        {
            prepararOpciones();
        }
    }

    public void iniciarJuego()
    {
        buttonText.setText("Mostrar otro País"); // Muestra el texto "Mostrar otro país"
        txtPais.setVisibility(View.VISIBLE); // Hace visible el país
        radioButtonGroup.setVisibility(View.VISIBLE); // Hace visible el grupo de radiobutton con las capitales
        comienzo = true; // Da valor true a la booleana comienzo
    }

    public void iniciarElementosGraficos() // Se inician los elementos gráficos
    {
        btnEmpezar = (ImageButton) findViewById(R.id.btnEmpezar);
        btnCambiarActividad = (Button) findViewById(R.id.btnCambiarActividad);
        radioButtonGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        txtPais = (TextView) findViewById(R.id.txtPais);
        buttonText = (TextView) findViewById(R.id.buttonText);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void iniciarBaseDatos()
    {
        // Se almacenan los países y capitales en el hashmap países
        paises.put("Angola", "Luanda");
        paises.put("Argelia", "Argel");
        paises.put("Benin", "Porto Novo");
        paises.put("Botsuana", "Gaborone");
        paises.put("Burkina F.", "Uagadugú");
        paises.put("Burundi", "Buyumbura");
        paises.put("Cabo Verde", "Praia");
        paises.put("Camerún", "Yaundé");
        paises.put("Chad", "Yamena");
        paises.put("Comoras", "Moroni");
        paises.put("Congo", "Brazzaville");
        paises.put("C.Marfil", "Yamusukro");
        paises.put("Egipto", "El Cairo");
        paises.put("Eritrea", "Asmara");
        paises.put("Esuatini", "Mbabane");
        paises.put("Etiopía", "Adís Abeba");
        paises.put("Gabón", "Libreville");
        paises.put("Gambia", "Banjul");
        paises.put("Ghana", "Acra");
        paises.put("Guinea", "Conakry");
        paises.put("Guinea Bissau", "Bissau");
        paises.put("Guinea Ecuatorial", "Malabo");
        paises.put("Kenya", "Nairobi");
        paises.put("Lesoto", "Maseru");
        paises.put("Liberia", "Monrovia");
        paises.put("Libia", "Trípoli");
        paises.put("Madagascar", "Antananarivo");
        paises.put("Malauí", "Lilongüe");
        paises.put("Malí", "Bamako");
        paises.put("Marruecos", "Rabat");
        paises.put("Mauricio", "Port Louis");
        paises.put("Mauritania", "Nuakchot");
        paises.put("Mozambique", "Maputo");
        paises.put("Namibia", "Windhoek");
        paises.put("Níger", "Niamey");
        paises.put("Nigeria", "Abuya");
        paises.put("República Centroafricana", "Bangui");
        paises.put("R. Democrática del Congo", "Kinshasa");
        paises.put("Ruanda", "Kigali");
        paises.put("Santo Tomé y Príncipe", "Santo Tomé");
        paises.put("Senegal", "Dakar");
        paises.put("Seychelles", "Victoria");
        paises.put("Sierra Leona", "Freetown");
        paises.put("Somalia", "Mogadiscio");
        paises.put("Sudáfrica", "Ciudad del Cabo");
        paises.put("Sudán", "Jartum");
        paises.put("Sudán del Sur", "Yuba");
        paises.put("Tanzania", "Dodoma");
        paises.put("Togo", "Lomé");
        paises.put("Túnez", "Túnez");
        paises.put("Uganda", "Kampala");
        paises.put("Yibuti", "Yibuti");
        paises.put("Zambia", "Lusaka");
        paises.put("Zimbabue", "Harare");

        // Se almacenan los países y capitales en el hashmap opciones
        opciones.put("Angola", "Luanda");
        opciones.put("Argelia", "Argel");
        opciones.put("Benin", "Porto Novo");
        opciones.put("Botsuana", "Gaborone");
        opciones.put("Burkina F.", "Uagadugú");
        opciones.put("Burundi", "Buyumbura");
        opciones.put("Cabo Verde", "Praia");
        opciones.put("Camerún", "Yaundé");
        opciones.put("Chad", "Yamena");
        opciones.put("Comoras", "Moroni");
        opciones.put("Congo", "Brazzaville");
        opciones.put("C.Marfil", "Yamusukro");
        opciones.put("Egipto", "El Cairo");
        opciones.put("Eritrea", "Asmara");
        opciones.put("Esuatini", "Mbabane");
        opciones.put("Etiopía", "Adís Abeba");
        opciones.put("Gabón", "Libreville");
        opciones.put("Gambia", "Banjul");
        opciones.put("Ghana", "Acra");
        opciones.put("Guinea", "Conakry");
        opciones.put("Guinea Bissau", "Bissau");
        opciones.put("Guinea Ecuatorial", "Malabo");
        opciones.put("Kenya", "Nairobi");
        opciones.put("Lesoto", "Maseru");
        opciones.put("Liberia", "Monrovia");
        opciones.put("Libia", "Trípoli");
        opciones.put("Madagascar", "Antananarivo");
        opciones.put("Malauí", "Lilongüe");
        opciones.put("Malí", "Bamako");
        opciones.put("Marruecos", "Rabat");
        opciones.put("Mauricio", "Port Louis");
        opciones.put("Mauritania", "Nuakchot");
        opciones.put("Mozambique", "Maputo");
        opciones.put("Namibia", "Windhoek");
        opciones.put("Níger", "Niamey");
        opciones.put("Nigeria", "Abuya");
        opciones.put("República Centroafricana", "Bangui");
        opciones.put("R. Democrática del Congo", "Kinshasa");
        opciones.put("Ruanda", "Kigali");
        opciones.put("Santo Tomé y Príncipe", "Santo Tomé");
        opciones.put("Senegal", "Dakar");
        opciones.put("Seychelles", "Victoria");
        opciones.put("Sierra Leona", "Freetown");
        opciones.put("Somalia", "Mogadiscio");
        opciones.put("Sudáfrica", "Ciudad del Cabo");
        opciones.put("Sudán", "Jartum");
        opciones.put("Sudán del Sur", "Yuba");
        opciones.put("Tanzania", "Dodoma");
        opciones.put("Togo", "Lomé");
        opciones.put("Túnez", "Túnez");
        opciones.put("Uganda", "Kampala");
        opciones.put("Yibuti", "Yibuti");
        opciones.put("Zambia", "Lusaka");
        opciones.put("Zimbabue", "Harare");

        int indice = 1;

        dbHandlerPaises.resetear(); // Se resetea la base de datos países para que vuelva a coger todos los países
        dbHandlerOpciones.resetear(); // Se resetea la base de datos opciones para que vuelva a coger todos los países

        for (Map.Entry<String, String> pair: paises.entrySet()) {
            dbHandlerPaises.insertarPais(new Pais(indice, pair.getKey(), pair.getValue())); // Inserta en la base de datos países
            indice++;
        }

        indice = 1;
        for (Map.Entry<String, String> pair: opciones.entrySet()) {
            dbHandlerOpciones.insertarPais(new Pais(indice, pair.getKey(), pair.getValue())); // Inserta en la base de datos opciones
            indice++;
        }
    }
}