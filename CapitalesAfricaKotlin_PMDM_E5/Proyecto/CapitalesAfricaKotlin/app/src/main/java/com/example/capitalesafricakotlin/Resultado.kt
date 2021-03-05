package com.example.capitalesafricakotlin

import java.io.Serializable

class Resultado: Serializable // Clase resultado (Serializable para poder hacer intent)
{
    var intentos: Int? = 0 // Atributo intentos
    var aciertos: Int? = 0 // Atributo aciertos
    var fallos: Int? = 0 // Atributo fallos

    constructor(fintetos: Int, faciertos: Int, ffallos: Int) // Constructor con intentos, aciertos y fallos)
    {
        this.intentos = fintetos
        this.aciertos = faciertos
        this.fallos = ffallos
    }
}