package com.example.capitalesafricakotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()
{

    val paises = mutableMapOf<String, String>( // Mutablemap que almacena listado de paises
        "Angola" to "Luanda",
        "Argelia" to "Argel",
        "Benin" to "Porto Novo",
        "Botsuana" to "Gaborone",
        "Burkina F." to "Uagadugú",
        "Burundi" to "Buyumbura",
        "Cabo Verde" to "Praia",
        "Camerún" to "Yaundé",
        "Chad" to "Yamena",
        "Comoras" to "Moroni",
        "Congo" to "Brazzaville",
        "C. Marfil" to "Yamusukro",
        "Egipto" to "El Cairo",
        "Eritrea" to "Asmara",
        "Esuatini" to "Mbabane",
        "Etiopía" to "Adís Abeba",
        "Gabón" to "Libreville",
        "Gambia" to "Banjul",
        "Ghana" to "Acra",
        "Guinea" to "Conakry",
        "Guinea Bissau" to "Bissau",
        "Guinea Ecuatorial" to "Malabo",
        "Kenya" to "Nairobi",
        "Lesoto" to "Maseru",
        "Liberia" to "Monrovia",
        "Libia" to "Trípoli",
        "Madagascar" to "Antananarivo",
        "Malauí" to "Lilongüe",
        "Malí" to "Bamako",
        "Marruecos" to "Rabat",
        "Mauricio" to "Port Louis",
        "Mauritania" to "Nuakchot",
        "Mozambique" to "Maputo",
        "Namibia" to "Windhoek",
        "Níger" to "Niamey",
        "Nigeria" to "Abuya",
        "República Centroafricana" to "Bangui",
        "R. Democrática del Congo" to "Kinshasa",
        "Ruanda" to "Kigali",
        "Santo Tomé y Príncipe" to "Santo Tomé",
        "Senegal" to "Dakar",
        "Seychelles" to "Victoria",
        "Sierra Leona" to "Freetown",
        "Somalia" to "Mogadiscio",
        "Sudáfrica" to "Ciudad del Cabo",
        "Sudán" to "Jartum",
        "Sudán del Sur" to "Yuba",
        "Tanzania" to "Dodoma",
        "Togo" to "Lomé",
        "Túnez" to "Túnez",
        "Uganda" to "Kampala",
        "Yibuti" to "Yibuti",
        "Zambia" to "Lusaka",
        "Zimbabue" to "Harare"
    )

    val opciones = mutableMapOf<String, String>( // Mutablemap que almacena listado de paises (opciones)
            "Angola" to "Luanda",
            "Argelia" to "Argel",
            "Benin" to "Porto Novo",
            "Botsuana" to "Gaborone",
            "Burkina F." to "Uagadugú",
            "Burundi" to "Buyumbura",
            "Cabo Verde" to "Praia",
            "Camerún" to "Yaundé",
            "Chad" to "Yamena",
            "Comoras" to "Moroni",
            "Congo" to "Brazzaville",
            "C. Marfil" to "Yamusukro",
            "Egipto" to "El Cairo",
            "Eritrea" to "Asmara",
            "Esuatini" to "Mbabane",
            "Etiopía" to "Adís Abeba",
            "Gabón" to "Libreville",
            "Gambia" to "Banjul",
            "Ghana" to "Acra",
            "Guinea" to "Conakry",
            "Guinea Bissau" to "Bissau",
            "Guinea Ecuatorial" to "Malabo",
            "Kenya" to "Nairobi",
            "Lesoto" to "Maseru",
            "Liberia" to "Monrovia",
            "Libia" to "Trípoli",
            "Madagascar" to "Antananarivo",
            "Malauí" to "Lilongüe",
            "Malí" to "Bamako",
            "Marruecos" to "Rabat",
            "Mauricio" to "Port Louis",
            "Mauritania" to "Nuakchot",
            "Mozambique" to "Maputo",
            "Namibia" to "Windhoek",
            "Níger" to "Niamey",
            "Nigeria" to "Abuya",
            "República Centroafricana" to "Bangui",
            "R. Democrática del Congo" to "Kinshasa",
            "Ruanda" to "Kigali",
            "Santo Tomé y Príncipe" to "Santo Tomé",
            "Senegal" to "Dakar",
            "Seychelles" to "Victoria",
            "Sierra Leona" to "Freetown",
            "Somalia" to "Mogadiscio",
            "Sudáfrica" to "Ciudad del Cabo",
            "Sudán" to "Jartum",
            "Sudán del Sur" to "Yuba",
            "Tanzania" to "Dodoma",
            "Togo" to "Lomé",
            "Túnez" to "Túnez",
            "Uganda" to "Kampala",
            "Yibuti" to "Yibuti",
            "Zambia" to "Lusaka",
            "Zimbabue" to "Harare"
    )

    var dbHandlerPaises = DbHandler(this, "capitalesAfricaPaises",1) // creación de tabla dbHandlerPaises en BD
    var dbHandlerOpciones = DbHandler(this, "capitalesAfricaOpciones",1) // creación de tabla dbHandlerOpciones en BD

    var paisActual = ""
    var aciertos = 0
    var fallos = 0
    var intentos = 0
    var comienzo = false



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciarBaseDatos() //Método que inicia la base de datos

        btnEmpezar.setOnClickListener{ //Acciones al pulsar el botón empezar
            try
            {
                jugar() // Método que inicia el juego
            }
                catch (e: Exception)
                {
                    if(dbHandlerPaises.obtenerTamano() > 0) // Controla que si el usuario no hace ninguna acción antes de pulsar el botón de  mostrar país, el contador siga sumando y continúe el juego mientras hayan países que mostrar
                    {
                        intentos++
                        fallos++
                        siguiente() //Método que controla que el juego continúe
                    }
                }
        }

        btnCambiarActividad.setOnClickListener { // Cuando se pulsa el botón de mostrar resultados cuando ya no quedan países en la bd
            Log.v(":::","Has pulsado el boton ver resultados") // Log que indica que se ha pulsado el botón ver resultados
            val resultado = Resultado(intentos,aciertos,fallos) // Objeto que pasa valor de intentos, aciertos y fallos
            val intent = Intent(this@MainActivity, Resultados::class.java) // Intent que hace que pasemos de mainActivity a Resultados
            intent.putExtra("valoresResultado", resultado) // Le pasa el objeto
            startActivity(intent) // Se inicia la activity
        }
    }



    private fun jugar()
    {
        if (!comienzo) // Si comienzo es true
        {
            iniciarJuego() // Inicia el juego y hace visible el país y los radiobutton con las opciones de capitales
        }
            else // Si comienzo es false
            {
                val eleccion = radioButtonGroup.checkedRadioButtonId
                val radioElegido: RadioButton = findViewById(eleccion) // Variable que almacena el Radiobutton elegido del RadioButtonGroup

                if(radioElegido.text == dbHandlerPaises.obtenerPaisPorNombre(paisActual)?.capital.toString()) // Comprueba si la capital seleccionada en el radiobutton es igual que el país
                {
                    intentos++ // Aumenta el valor de intentos
                    aciertos++ // Aumenta el valor de aciertos
                }
                    else
                    {
                        intentos++
                        fallos++
                    }
            }

        radioButtonGroup.clearCheck() // Limpia la selección del grupo de radiobutton
        siguiente() // Método que controla que el juego continúe
    }



    private fun iniciarJuego()
    {
        buttonText.text = "Mostrar otro País" // Muestra el texto "Mostrar otro país"
        txtPais.visibility = View.VISIBLE // Hace visible el país
        radioButtonGroup.visibility = View.VISIBLE // Hace visible el grupo de radiobutton
        comienzo = true // Da valor true a la booleana comienzo
    }



    private fun siguiente()
    {
        if(dbHandlerPaises.obtenerTamano() <= 1) // Si no hay países en la bbdd dbHandlerPaises
        {
            btnCambiarActividad.visibility = View.VISIBLE //Hace visible el boton de ver resultados que pasa al otro activity para ver resultados
            btnEmpezar.visibility = View.INVISIBLE // Hace invisible el botón empezar
            radioButtonGroup.visibility = View.INVISIBLE // Hace invisible el grupo de radiobutton
            txtPais.visibility = View.INVISIBLE // Hace invisible el país
            buttonText.visibility = View.INVISIBLE // Hace invisible el texto de "Pulse para empezar"
            textView.text = "Fin del juego" // Aparece un mensaje de "Fin del juego"
        }
            else // Si siguien habiendo paísese en la bbdd dbHandlerPaises
            {
                dbHandlerPaises.borrarPais(paisActual) // Se elimina el país de la bbdd dbHandlerPaises
                var id = (0..54).random()

                do {
                    id = (0..54).random() // Se hace un random para obtener un nuevo país
                }
                while(dbHandlerPaises.obtenerPaisPorID(id)?.capital.equals(null)) // Se hace el random hasta que encuentre un país cuya capital no sea nula.


                paisActual = dbHandlerPaises.obtenerPaisPorID(id)?.pais.toString() // obtiene un nuevo país
                txtPais.text = paisActual // muestra el nuevo país

                prepararOpciones() // Método que hace que se muestren 4 opciones de capitales
            }
    }


    private fun prepararOpciones()
    {
        radioButton1.text = dbHandlerOpciones.obtenerPaisPorID((0..54).random())?.capital.toString() // Muestra una capital random de las capitales almacenadas en dbHandlerOpciones
        radioButton2.text = dbHandlerOpciones.obtenerPaisPorID((0..54).random())?.capital.toString() // Muestra una capital random de las capitales almacenadas en dbHandlerOpciones
        radioButton3.text = dbHandlerOpciones.obtenerPaisPorID((0..54).random())?.capital.toString() // Muestra una capital random de las capitales almacenadas en dbHandlerOpciones
        radioButton4.text = dbHandlerOpciones.obtenerPaisPorID((0..54).random())?.capital.toString() // Muestra una capital random de las capitales almacenadas en dbHandlerOpciones

        var respuestaCorrecta = dbHandlerPaises.obtenerPaisPorNombre(paisActual)?.capital.toString() //Almacena en una variable la capital correcta del país que se mostrará

        // Entrará en el método prepararOpciones hasta que los radiobutton no se repitan entre si, no sean nulos, y alguno de ellos contenga la capital correcta del país que se muestra
        if( (radioButton1.text != respuestaCorrecta && radioButton2.text != respuestaCorrecta && radioButton3.text != respuestaCorrecta && radioButton4.text != respuestaCorrecta) || (radioButton1.text == radioButton2.text || radioButton1.text == radioButton3.text || radioButton1.text == radioButton4.text || radioButton2.text == radioButton3.text || radioButton2.text == radioButton4.text || radioButton3.text == radioButton4.text) || (radioButton1.text.equals("null") || radioButton2.text.equals("null") || radioButton3.text.equals("null") || radioButton4.text.equals("null")) )
        {
            prepararOpciones()
        }
    }


    fun iniciarBaseDatos() //Método que inicia la base de datos
    {
        var indice = 1

        dbHandlerPaises.resetear() //Resetea la base de datos dbHandlerPaises para que aparezcan todos los países y sus capitales
        dbHandlerOpciones.resetear() //Resetea la base de datos dbHandlerOpciones para que aparezcan todos los países y sus capitales

        for ((pais, capital) in paises) // Recorre el Mutablemap paises
        {
            dbHandlerPaises.insertarPais(Pais(indice, pais, capital)) //inserta índice, país y capital de cada uno de los países del map
            indice++ // aumentamos el valor de índice
        }

        indice = 1
        for ((pais, capital) in opciones) // Recorre el Mutablemap opciones
        {
            dbHandlerOpciones.insertarPais(Pais(indice, pais, capital)) //inserta índice, país y capital de cada uno de los países del map
            indice++ // aumentamos el valor de índice
        }
    }

}