package com.example.rachas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),OnClickElement {

    lateinit var recicler:RecyclerView
    var elementos = ArrayList<Elemento>()
    var adaptador = Adaptador(elementos,this,this)
    val serializador = Serializador(this,"elementos.json")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.nuevo){
            val intent = Intent(this,NuevoElemento::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        guardar()

    }

    private fun initComponents() {

        recicler = findViewById<RecyclerView>(R.id.lista)
        val manager = LinearLayoutManager(this)
        recicler.layoutManager = manager
        elementos = serializador.leer()

        setAdaptador()
    }

    public fun nuevoElemento(nombre:String,imagen:String){
        elementos.add(Elemento(nombre,imagen,0))
        Log.i("info","elementos $elementos")
        setAdaptador()
        guardar()
    }

    override fun click(posicion: Int) {
        val intent = Intent(this,VerElemento::class.java)
        putSharedPreference(posicion, elementos[posicion].contador, elementos[posicion].nombre,elementos[posicion].imagen)
        startActivity(intent)
    }

    override fun onLongClick(posicion: Int) {
        elementos.removeAt(posicion)
    }

    private fun setAdaptador() {
        adaptador = Adaptador(elementos,this,this)
        recicler.adapter = adaptador
    }

    private fun guardar(){
        serializador.guardar(elementos)
    }

    private fun putSharedPreference(posicion:Int,racha:Int,nombre:String,imagen: String){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        val editor = preferencias.edit()
        editor.putInt("posicion",posicion)
        editor.putInt("racha",racha)
        editor.putString("nombre",nombre)
        editor.putString("verImagen",imagen)

        editor.apply()
    }

    override fun onResume() {
        super.onResume()

        getPreferencias()
        setAdaptador()
    }

    private fun getPreferencias(){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        Log.i("info","""nuevo ${preferencias.getBoolean("nuevo",false)}""")
        Log.i("info","""segunda ${preferencias.getBoolean("segunda", false)}""")

        when {
            preferencias.getBoolean("segunda", false)-> {

                elementos[preferencias.getInt("posicion", -1)].contador = preferencias.getInt("racha", -1)
                val editor = preferencias.edit()
                editor.putBoolean("segunda", false)
                editor.apply()
            }

                preferencias.getBoolean("nuevo", false) -> {

                    Log.i("info","entro a nuevo")
                    val imagen = preferencias.getString("imagen","null")
                    val nombre = preferencias.getString("nuevoNombre","null")

                    if (nombre != null && imagen != null) {
                        nuevoElemento(nombre,imagen)
                    }

                    val editor = preferencias.edit()
                    editor.putBoolean("nuevo", false)
                    editor.apply()
            }
        }

    }
}