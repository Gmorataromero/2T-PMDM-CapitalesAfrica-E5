package com.example.capitalesafricakotlin

class Pais // Clase país
{
    var id: Int? = 0 // Atributo id
    var pais: String? = null // Atributo país
    var capital: String? = null // Atributo capital

    constructor(fid: Int, fpais: String, fcapital: String) // Constructor con id, pais y capital
    {
        this.id = fid
        this.pais = fpais
        this.capital = fcapital
    }
}