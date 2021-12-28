package com.example.rachas

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class NuevoElemento():AppCompatActivity() {

    private val SELECT_ACTIVITY = 50
    private var imageUri:Uri? = null
    private lateinit var ok:Button
    private lateinit var nombre:TextView
    private lateinit var imagen:ImageView

    /**
     * ejecutado al iniciar la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nuevo)

        initComponents()
    }

    /**
     * inicializacion de los componentes a utilizar y los OnClickListenr del boton ok y la imagen
     */
    private fun initComponents() {
        nombre = findViewById<TextView>(R.id.nombre)
        ok = findViewById<Button>(R.id.ok)
        imagen = findViewById<ImageView>(R.id.seleccionarImagen)

        ok.setOnClickListener{
            crearElemento()
            startActivity(Intent(this,MainActivity::class.java))
        }

        imagen.setOnClickListener{
            ImageController.seleccionarFoto(this,SELECT_ACTIVITY)
        }
    }

    /**
     * se ejecuta cuando acaba la actividad, setea la imagen de seleccion de imagen a la imagen seleccionada solo si consuerdan el codigo recibido y el
     * codigo de resultado es el codigo de "OK"
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                imageUri = data!!.data
                imagen.setImageURI(imageUri)
            }
        }
    }

    /**
     * una vez se tienen los datos que queremos guardar se crea el elemento pasando los datos a las SharedPreferences que recibira la MainActivity
     */
    private fun crearElemento(){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        val editor = preferencias.edit()
        val idImagen = generarId()

        editor.putBoolean("nuevo",true)
        editor.putString("nuevoNombre",nombre.text.toString())
        editor.putString("imagen",idImagen)
        editor.apply()

        imageUri?.let { ImageController.guardarImagen(this,idImagen, it) }
    }

    /**
     * genera una id random apoyandose en la clase IdGenerator, si esta id ya existiese se llama de nuevo al metodo hasta que se cree una id no existente
     * cuando se consiga una id no exstente se guarda en el json de almacenaje de ids
     */
    private fun generarId():String{
        var id = IdGenerator.generarId()
        if(checkIdNotexists(id)){
            id = IdGenerator.generarId()
        }

        guardarId(id)
        return id
    }

    /**
     * checkea si la id exite o no en el json de ids
     * @property id a checkear
     * @return si existe o no la id
     */
    private fun checkIdNotexists(id:String):Boolean{
        val serializador = Serializador(this,"ids.json")
        return serializador.leerId().contains(id)
    }

    /**
     * guarda la id en el json de ids
     * @property id a guardar
     */
    private fun guardarId(id:String){
        val serializador = Serializador(this,"ids.json")
        val ids = serializador.leerId()
        ids.add(id)
        serializador.guardarId(ids)
    }
}