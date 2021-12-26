package com.example.rachas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File

object ImageController {
    public fun seleccionarFoto(activity: Activity, codigo:Int){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent,codigo)
    }

    public fun guardarImagen(contexto: Context, id:String, uri:Uri){
        val file = File(contexto.filesDir,id)
        val bytes = contexto.contentResolver.openInputStream(uri)?.readBytes()!!

        file.writeBytes(bytes)
    }

    public fun getImagen(contexto:Context, id:String):Uri{
        val file = File(contexto.filesDir,id)
        if(id!="null"){
            return Uri.fromFile(file)
        }
        return Uri.parse("android.resource://com.example.rachas/drawable/ic_launcher_foreground")
    }
}