package com.example.capitalesafricakotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHandler(context: Context?, nombreBD: String, dbVersion: Int) : SQLiteOpenHelper(context, nombreBD, null, dbVersion)
{
    val TABLE_PAISES = "paises"
    val KEY_ID = "id"
    val KEY_PAIS = "pais"
    val KEY_CAPITAL = "capital"

    override fun onCreate(db: SQLiteDatabase) // Se crea la tabla
    {
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_PAISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PAIS + " TEXT,"
                + KEY_CAPITAL + " TEXT" + ")")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAISES) // Elimina la antigua tabla si ya existe una creada
        onCreate(db) // Crea la tabla
    }


    fun resetear() // Elimina la tabla si ya existe
    {
        val db = this.writableDatabase // La base de datos se abre en modo lectura y escritura
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAISES) // Sentencia sql
        onCreate(db) // crea la tabla
    }


    fun insertarPais(pais: Pais) // Inserta un país completo: id, país y capital
    {
        val db = this.writableDatabase // La base de datos se abre en modo lectura y escritura
        val cValues = ContentValues() // Hace que se inserte una nueva fila en la tabla
        cValues.put(KEY_ID, pais.id) // Pasamos id
        cValues.put(KEY_PAIS, pais.pais) // Pasamos país
        cValues.put(KEY_CAPITAL, pais.capital) // Pasamos capital

        db.insert(TABLE_PAISES, null, cValues) // Inserta los datos que se le han pasado
        db.close() // Cierra la base de datos
    }


    fun obtenerPaisPorID(id: Int): Pais? // Selecciona de la tabla un país pasándole por parámetro el id del país
    {
        val db = this.writableDatabase // La base de datos se abre en modo lectura y escritura
        val query ="SELECT * FROM $TABLE_PAISES WHERE $KEY_ID =  \"$id\"" // Sentencia sql
        val cursor = db.rawQuery(query, null) // Se inicializa la variable cursor con el valor devuelto por el método rawQuery
        var pais: Pais? = null

        if (cursor.moveToFirst()) // Mueve el cursor a la primera fila y compreba si el cursor está vacío o no
        {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0)) // obtiene el id
            val nombrePais = cursor.getString(1) //  obtiene el país
            val capital = cursor.getString(2) // obtiene la capital
            pais = Pais(id, nombrePais, capital) // crea un objeto país con los datos obtenidos
        }

        cursor.close() // se cierra el cursor
        db.close() // se cierra la base de datos

        return pais // devuelve el país
    }


    fun obtenerPaisPorNombre(pais: String): Pais? // Selecciona de la tabla un país pasándole por parámetro el país
    {
        val db = this.writableDatabase // La base de datos se abre en modo lectura y escritura
        val query ="SELECT * FROM $TABLE_PAISES WHERE $KEY_PAIS =  \"$pais\"" // Sentencia sql
        val cursor = db.rawQuery(query, null) // Se inicializa la variable cursor con el valor devuelto por el método rawQuery
        var pais: Pais? = null

        if (cursor.moveToFirst()) // Mueve el cursor a la primera fila y compreba si el cursor está vacío o no
        {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0)) // obtiene el id
            val nombrePais = cursor.getString(1) //  obtiene el país
            val capital = cursor.getString(2) // obtiene la capital
            pais = Pais(id, nombrePais, capital) // crea un objeto país con los datos obtenidos
        }

        cursor.close() // se cierra el cursor
        db.close()  // se cierra la base de datos

        return pais // devuelve el país
    }


    fun obtenerTamano(): Int // Obtiene el tamaño de la base de datos
    {
        var tamano = 0 // Se inicializa una variable tamaño a 0

        val db = this.writableDatabase // La base de datos se abre en modo lectura y escritura
        val query = "SELECT * FROM " + TABLE_PAISES // sentencia sql
        val cursor = db.rawQuery(query, null) // Se inicializa la variable cursor con el valor dvuelto por el método rawQuery

        while(cursor.moveToNext()) // Mientras hayan datos
        {
            tamano++ // La variable tamaño aumenta su valor en 1
        }

        cursor.close() // se cierra el cursor
        db.close() // se cierra la base de datos

        return tamano // devuelve el tamaño
    }


    fun borrarPais(pais: String) // Elimina de la tabla un país pasándole por parámetro el id del país
    {
        val db = this.writableDatabase // La base de datos se abre en modo lectura y escritura
        val query ="SELECT * FROM $TABLE_PAISES WHERE $KEY_PAIS = \"$pais\"" // sentencia sql
        val cursor = db.rawQuery(query, null) // Se inicializa la variable cursor con el valor dvuelto por el método rawQuery

        if(cursor.moveToFirst()) // Mueve el cursor a la primera fila y compreba si  está vacío o no
        {
            val id = Integer.parseInt(cursor.getString(0)) // obtiene el id
            db.delete(TABLE_PAISES, KEY_ID + " = " + id,null) // elimina el país de la tabla
        }

        cursor.close() // se cierra el cursor
        db.close() // se cierra la base de datos
    }
}