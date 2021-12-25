package com.example.rachas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VerElemento(): AppCompatActivity() {
    lateinit var imagen:ImageView
    lateinit var nombre:TextView
    lateinit var multiplicador:EditText
    lateinit var mas:ImageView
    lateinit var menos:ImageView
    lateinit var actual:TextView
    var posicion = 0
    var computo = 1
    var valorActual = 0
    lateinit var elemento:Elemento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit)

        initComponents()
    }

    /**
     * component initialice
     */
    private fun initComponents() {
        imagen = findViewById<ImageView>(R.id.imagenEdit)
        nombre = findViewById<TextView>(R.id.nombreEdit)
        multiplicador = findViewById<EditText>(R.id.contadorEdit)
        mas = findViewById<ImageView>(R.id.mas)
        menos = findViewById<ImageView>(R.id.menos)
        actual = findViewById<TextView>(R.id.countEdit)
        getPreferencias()

        nombre.text = elemento.nombre
        actual.text = "Cantidad actual -> ${elemento.contador}"

        onClick()

    }

    /**
     * onCLick listeners
     */
    private fun onClick() {
        mas.setOnClickListener(View.OnClickListener { view -> calcular(true) })
        menos.setOnClickListener(View.OnClickListener { view -> calcular(false) })
    }

    /**
     * change multiplier
     */
    private fun editMultiplier(){
        computo = multiplicador.text.toString().toInt()
    }

    /**
     * add or substract
     */
    private fun calcular(suma:Boolean){
        //Log.i("info","""a ${multiplicador.text.equals("")}""")
        editMultiplier()

        if(suma) {
            elemento.contador += computo
        }else {
            elemento.contador -= computo
        }

        actual.text = "Cantidad actual -> ${elemento.contador}"
        putSharedPreference(elemento.contador)
    }

    private fun putSharedPreference(racha:Int){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        val editor = preferencias.edit()
        editor.putInt("racha",racha)
        editor.putBoolean("segunda",true)
        editor.commit()
    }

    private fun getPreferencias(){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        var nombre = preferencias.getString("nombre","algo")

        Log.i("info","""vernota ${preferencias.getInt("posicion", -1)}""")
        elemento = nombre?.let { Elemento(it,-1,preferencias.getInt("racha",0)) }!!

    }
}