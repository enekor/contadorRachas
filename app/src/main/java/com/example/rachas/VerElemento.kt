package com.example.rachas

import android.os.Bundle
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
     * inicializacion de los componentes
     */
    private fun initComponents() {
        imagen = findViewById<ImageView>(R.id.imagenEdit)
        nombre = findViewById<TextView>(R.id.nombreEdit)
        multiplicador = findViewById<EditText>(R.id.contadorEdit)
        mas = findViewById<ImageView>(R.id.mas)
        menos = findViewById<ImageView>(R.id.menos)
        actual = findViewById<TextView>(R.id.countEdit)
        getPreferencias()

        imagen.setImageURI(ImageController.getImagen(this,elemento.imagen))
        nombre.text = elemento.nombre
        actual.text = "${elemento.contador}"

        onClick()

    }

    /**
     * adicion de los OnClick a los elementos que lo usaran
     */
    private fun onClick() {
        mas.setOnClickListener{ calcular(true) }
        menos.setOnClickListener{ calcular(false) }
    }

    /**
     * cambia el numero operador
     */
    private fun editMultiplier(){
        computo = multiplicador.text.toString().toInt()
    }

    /**
     * suma o resta el nuemro operador dependiendo de si se quiere sumar o restar
     * @property suma si es true se espera una suma y si es false se espera una resta
     */
    private fun calcular(suma:Boolean){
        //Log.i("info","""a ${multiplicador.text.equals("")}""")
        editMultiplier()

        if(suma) {
            elemento.contador = (elemento.contador.toInt()+computo).toString()
        }else {
            elemento.contador = (elemento.contador.toInt()-computo).toString()
        }

        actual.text = elemento.contador
        putSharedPreference(elemento.contador)
    }

    /**
     * guarda en las SharedPreferences los datos a recibir en el MainActivity
     * @property racha la racha modificada a guardar
     */
    private fun putSharedPreference(racha:String){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        val editor = preferencias.edit()
        editor.putString("racha",racha)
        editor.putBoolean("segunda",true)
        editor.commit()
    }

    /**
     * recojemos los datos desde las SharedPrefrences y creamos un elemento sobre el que actuaremos durante el proceso de esta actividad
     */
    private fun getPreferencias(){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        val posicion = preferencias.getInt("posicion",-1)
        val racha = preferencias.getString("racha","0")
        val nombre = preferencias.getString("nombre","algo")
        val imagen = preferencias.getString("verImagen","")

        if (nombre!=null && imagen!=null){
            elemento = racha?.let { Elemento(nombre,imagen, it) }!!
        }
        this.posicion = posicion
    }
}