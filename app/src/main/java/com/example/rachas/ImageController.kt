package com.example.rachas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File

object ImageController {

    /**
     * seleccionamos la foto desde la galeria, usando la actividad como base
     * @property activity actividad desde la que accedemos
     * @property codigo codigo con el que identificamos la operacion
     */
    public fun seleccionarFoto(activity: Activity, codigo:Int){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent,codigo)
    }

    /**
     * gurdamos la imagen en memoria
     * @property contexto contexto desde el que accedemos a la memoria del dispositivo
     * @property id id de la imagen por la que podremos acceder a ella mas tarde o en otro momento
     * @property uri uri de la imagen a guardar
     */
    public fun guardarImagen(contexto: Context, id:String, uri:Uri){
        val file = File(contexto.filesDir,id)
        val bytes = contexto.contentResolver.openInputStream(uri)?.readBytes()!!

        file.writeBytes(bytes)
    }

    /**
     * obtenemos la imagen guardada en memoria
     * @property contexto contexto desde el que accedemos a la memoria del dispositivo
     * @property id id de la imagen almacenada
     * @return la uri de la imagen a la que queremos acceder
     */
    public fun getImagen(contexto:Context, id:String):Uri{
        val file = File(contexto.filesDir,id)
        if(id!="null"){
            return Uri.fromFile(file)
        }
        return Uri.parse("android.resource://com.example.rachas/drawable/default_image")
    }
}