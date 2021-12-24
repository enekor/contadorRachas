package com.example.rachas

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.Exception
import java.lang.reflect.Type

class Serializador(context:Context,archivo:String) {
    val gson = Gson()
    val contexto = context
    val nombre = archivo

    public fun leer():ArrayList<Elemento>{
        try{
            val out = InputStreamReader(contexto.openFileInput(nombre))
            val reader = BufferedReader(out)
            val type = object : TypeToken<ArrayList<Elemento>>() {}.type
            return gson.fromJson<ArrayList<Elemento>>(reader,type) ?: ArrayList<Elemento>()
        }catch (e:Exception){
            return ArrayList<Elemento>()
        }
    }

    public fun guardar(lista:ArrayList<Elemento>){
        val writer = OutputStreamWriter(contexto.openFileOutput(nombre,Context.MODE_PRIVATE))
        writer.write(gson.toJson(lista))
        writer.flush()
        writer.close()
    }
}

























