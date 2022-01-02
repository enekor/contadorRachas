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
     * lee los datos guardado en el json
     * @param T de que tipo es la lista a leer
     * @property archivo el nombre del archivo json (*.json)
     */
    inline fun<reified T> leer(archivo:String):ArrayList<T>{
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
     * guarda la lista actualizada en el archivo json adecuado
     * @param T de que tipo es la lista a guardar
     * @property lista la lista a guardar en el json
     * @property archivo el nombre del archivo json (*.json)
     */
    inline fun<reified T> guardar(lista:ArrayList<T>,archivo: String){
        val writer = OutputStreamWriter(contexto.openFileOutput(archivo,Context.MODE_PRIVATE))
        writer.write(gson.toJson(lista))
        writer.flush()
        writer.close()
    }
}

























