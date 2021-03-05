package com.example.capitalesafricajava;

import java.io.Serializable;

public class Resultado implements Serializable // Clase resultado (Serializable para poder hacer intent)
{
    private int intentos; // Atributo intentos
    private int aciertos; // Atributo aciertos
    private int fallos; // Atributo fallos

    public Resultado(int intentos, int aciertos, int fallos) { // Constructor con intentos, aciertos y fallos
        this.intentos = intentos;
        this.aciertos = aciertos;
        this.fallos = fallos;
    }

    public Resultado() { // Constructor vacío
    }

    // Getter y setter
    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    public int getFallos() {
        return fallos;
    }

    public void setFallos(int fallos) {
        this.fallos = fallos;
    }
}