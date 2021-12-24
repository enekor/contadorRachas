package com.example.rachas

import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlin.properties.Delegates

class VerElemento(): AppCompatActivity(),View.OnClickListener {
    lateinit var imagen:ImageView
    lateinit var nombre:TextView
    lateinit var contador:EditText
    lateinit var mas:ImageView
    lateinit var menos:ImageView
    lateinit var elementos:ArrayList<Elemento>
    val serializador = Serializador(this,"elementos.json")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit)

        initComponents()

    }

    private fun initComponents() {
        imagen = findViewById<ImageView>(R.id.imagenEdit)
        nombre = findViewById<TextView>(R.id.nombreEdit)
        contador = findViewById<EditText>(R.id.contadorEdit)
        mas = findViewById<ImageView>(R.id.mas)
        menos = findViewById<ImageView>(R.id.menos)
        elementos = serializador.leer()
    }

    override fun onClick(v: View?) {
        val posicion = intent.getIntExtra("posicion",-1)
        when (v?.id){
            menos.id -> { elementos.get(posicion).contador+=1 }
            mas.id -> { elementos.get(posicion).contador -= contador.text as Int }
        }
    }

    override fun onPause() {
        super.onPause()
        serializador.guardar(elementos)
    }
}