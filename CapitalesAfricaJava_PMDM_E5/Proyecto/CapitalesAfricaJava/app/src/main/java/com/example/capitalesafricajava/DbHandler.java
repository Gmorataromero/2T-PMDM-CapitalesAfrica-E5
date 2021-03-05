package com.example.capitalesafricajava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHandler extends SQLiteOpenHelper
{
    private static final String TABLE_PAISES = "paises";
    private static final String KEY_ID = "id";
    private static final String KEY_PAIS = "pais";
    private static final String KEY_CAPITAL = "capital";

    public DbHandler(Context context, String dbName, int dbVersion)
    {
        super(context,dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){ // Se crea la tabla
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PAISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PAIS + " TEXT,"
                + KEY_CAPITAL + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAISES); // Elimina la antigua tabla si ya existe una creada
        onCreate(db); // crea la tabla
    }

    public void resetear()
    {
        SQLiteDatabase db = this.getWritableDatabase(); // La base de datos se abre en modo lectura y escritura
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAISES); // Elimina la antigua tabla si ya existe una creada
        onCreate(db); // crea la tabla
    }

    void insertarPais(Pais pais) // Inserta un país completo: id, país y capital
    {
        SQLiteDatabase db = this.getWritableDatabase(); // La base de datos se abre en modo lectura y escritura

        ContentValues cValues = new ContentValues(); // Contenedor de los valores del insert
        cValues.put(KEY_ID, pais.getId()); // Pasamos id
        cValues.put(KEY_PAIS, pais.getPais()); // Pasamos país
        cValues.put(KEY_CAPITAL, pais.getCapital()); // Pasamos capital

        db.insert(TABLE_PAISES,null, cValues); // Inserta los datos que se le han pasado
        db.close(); // Cierra la base de datos
    }


    public Pais obtenerPaisPorID(int paisID) // Selecciona de la tabla un país pasándole por parámetro el id del país
    {
        Pais paisEncontrado = new Pais(); // Objeto País

            SQLiteDatabase db = this.getWritableDatabase(); // La base de datos se abre en modo lectura y escritura
            Cursor cursor = db.query(TABLE_PAISES, new String[]{KEY_ID, KEY_PAIS, KEY_CAPITAL}, KEY_ID + "=?",new String[]{String.valueOf(paisID)},null, null, null, null); // Se almacena la consulta en un cursor


            if (cursor.moveToFirst()) // Mueve el cursor a la primera fila y comprueba si está vacío o no
            {
                cursor.moveToFirst();

                paisEncontrado.setId(cursor.getInt(0)); // obtiene el id
                paisEncontrado.setPais(cursor.getString(1)); //  obtiene el país
                paisEncontrado.setCapital(cursor.getString(2)); // obtiene la capital
            }
                else
                {
                    paisEncontrado.setPais("error"); // Devuelve error en el país
                }

            cursor.close(); // Se cierra el cursor
            db.close(); // Se cierra la base de datos

        return paisEncontrado; // Devuelve el país
    }

    public Pais obtenerPaisPorNombre(String paisNombre) // Selecciona de la tabla un país pasándole por parámetro el nombre del país
    {
        Pais paisEncontrado = new Pais(); // Objeto país

        if(paisNombre != null) // Si el nombre del país no es nulo
        {
            SQLiteDatabase db = this.getWritableDatabase(); // La base de datos se abre en modo lectura y escritura
            Cursor cursor = db.query(TABLE_PAISES, new String[]{KEY_ID, KEY_PAIS, KEY_CAPITAL}, KEY_PAIS + "=?",new String[]{paisNombre},null, null, null, null); // Se almacena la consulta en un cursor

            if (cursor.moveToFirst()) // Mueve el cursor a la primera fila y comprueba si está vacío o no
            {
                cursor.moveToFirst();

                paisEncontrado.setId(cursor.getInt(0)); // Obtiene el id
                paisEncontrado.setPais(cursor.getString(1)); // Obtiene el país
                paisEncontrado.setCapital(cursor.getString(2)); // Obtiene la capital
            }

            cursor.close(); // Se cierra el cursor
            db.close(); // Se cierra la base de datos
        }

        return paisEncontrado; // Devuelve el país
    }

    public int obtenerTamano() // Obtiene el tamaño de la base de datos
    {
        int tamano = 0; // Se inicializa la variable tamaño a 0

        SQLiteDatabase db = this.getWritableDatabase(); // La base de datos se abre en modo lectura y escritura
        String query = "SELECT * FROM " + TABLE_PAISES; // sentencia sql
        Cursor cursor = db.rawQuery(query, null); // Se almacena la consulta en un cursor

        while(cursor.moveToNext()) // Mientras hayan datos
        {
            tamano++; // La variable tamaño aumenta su valor en 1
        }

        cursor.close(); // Se cierra el cursor
        db.close(); // Se cierra la base de datos

        return tamano; // Devuelve el tamaño
    }

    public void eliminarPais(String pais) // Elimina de la tabla un país pasándole por parámetro el id del país
    {
        if(pais != null) // Si no es nulo
        {
            SQLiteDatabase db = this.getWritableDatabase(); // La base de datos se abre en modo lectura y escritura
            Cursor cursor = db.query(TABLE_PAISES, new String[]{KEY_ID, KEY_PAIS, KEY_CAPITAL}, KEY_PAIS + "=?",new String[]{pais},null, null, null, null); // Se muestran los datos de la tabla y se almacenan en un cursor

            if(cursor.moveToFirst()) // Mueve el cursor a la primera fila y comprueba si está vacío o no
            {
                int id = Integer.parseInt(cursor.getString(0)); // Obtiene el id
                db.delete(TABLE_PAISES, KEY_ID + " = ?",new String[]{String.valueOf(id)}); // Elimina el país de la tabla
            }

            cursor.close(); // Se cierra el cursor
            db.close(); // Se cierra la base de datos
        }

    }

}