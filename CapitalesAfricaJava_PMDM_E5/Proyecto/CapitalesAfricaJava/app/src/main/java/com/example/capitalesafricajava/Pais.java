package com.example.capitalesafricajava;

public class Pais // Clase país
{
    private int id; // Atributo id
    private String pais; // Atributo país
    private String capital; // Atributo capital

    public Pais(int id, String pais, String capital) { // Constructor con el id, país y capital
        this.id = id;
        this.pais = pais;
        this.capital = capital;
    }

    public Pais() { // Constructor vacío
    }

    // Getter y setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
