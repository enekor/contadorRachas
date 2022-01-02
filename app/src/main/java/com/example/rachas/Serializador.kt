package com.example.rachas

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.Exception

class Serializador(context:Context) {
    val gson = Gson()
    val contexto = context

    /**
     * lee el json con los datos almacenados
     * @return una arraylist de Ts con los datos almacenados o una arraylist vacia si da problemas porque no existe el archivo de almacenaje de datos
     */
    public inline fun<reified T> leer(archivo:String):ArrayList<T>{
        return try{
            val out = InputStreamReader(contexto.openFileInput(archivo))
            val reader = BufferedReader(out)
            val type = object : TypeToken<ArrayList<T>>(){}.type
            gson.fromJson(reader,type)
        }catch (e:Exception){
            ArrayList<T>()
        }
    }

    /**
     * guarda la arraylist de Ts en el json
     * @property lista la lista de Ts que vamos a guardar
     */
    public inline fun<reified T> guardar(lista:ArrayList<T>,archivo: String){
        val writer = OutputStreamWriter(contexto.openFileOutput(archivo,Context.MODE_PRIVATE))
        writer.write(gson.toJson(lista))
        writer.flush()
        writer.close()
    }
/*
    /**
     * lee el json con las id almacenadas
     * @return una arraylist de ids o una vacia en el caso de no existencia del archivo
     */
    public fun leerId(nombre:String):ArrayList<String>{
        try{
            val out = InputStreamReader(contexto.openFileInput(nombre))
            val reader = BufferedReader(out)
            val type = object : TypeToken<ArrayList<String>>() {}.type
            return gson.fromJson<ArrayList<String>>(reader,type) ?: ArrayList<String>()
        }catch (e:Exception){
            return ArrayList<String>()
        }
    }

    /**
     * guarda la arraylist de ids
     * @property lista la lista de ids a guardar
     */
    public fun guardarId(ids:ArrayList<String>,nombre:String){
        val writer = OutputStreamWriter(contexto.openFileOutput(nombre,Context.MODE_PRIVATE))
        writer.write(gson.toJson(ids))
        writer.flush()
        writer.close()
    }
    */
}

























