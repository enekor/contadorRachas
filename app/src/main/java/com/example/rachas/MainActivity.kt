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
    var adaptador = Adaptador(elementos,this)
    val serializador = Serializador(this,"elementos.json")

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.nuevo){
            val nuevoElemento = NuevoElemento(this)
            nuevoElemento.show(supportFragmentManager,null)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
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

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        if(isTopResumedActivity) {
            getPreferencias()
            setAdaptador()
        }
    }

    public fun nuevoElemento(elemento:Elemento){
        elementos.add(elemento)
        Log.i("info","elementos $elementos")
        setAdaptador()
        guardar()
    }

    override fun click(posicion: Int) {
        val intent = Intent(this,VerElemento::class.java)
        putSharedPreference(posicion, elementos[posicion].contador, elementos[posicion].nombre)
        startActivity(intent)
    }

    private fun setAdaptador() {
        adaptador = Adaptador(elementos,this)
        recicler.adapter = adaptador
    }

    private fun guardar(){
        serializador.guardar(elementos)
    }

    private fun putSharedPreference(posicion:Int,racha:Int,nombre:String){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        val editor = preferencias.edit()
        editor.putInt("posicion",posicion)
        editor.putInt("racha",racha)
        editor.putString("nombre",nombre)

        editor.commit()
    }

    private fun getPreferencias(){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)

        if(preferencias.getBoolean("segunda",false)) {

            Log.i("info","""mainactivity ${preferencias.getInt("posicion", -1)}""")
            elementos[preferencias.getInt("posicion", -1)].contador = preferencias.getInt("racha", -1)
        }

        val editor = preferencias.edit()
        editor.putBoolean("segunda",false)
    }
}