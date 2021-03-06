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

    /**
     * metodo ejecutado al iniciar le programa
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
    }

    /**
     * inflar el menu de la actividad
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * ejecutado cunado se interactua con una opcion del menu, si es el boton $nuevo llama a la actividad de creacion de un nuevo $Elemento
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.nuevo -> {
                val intent = Intent(this, NuevoElemento::class.java)
                startActivity(intent)
            }
            item.itemId == R.id.filtro -> {
                orderLayout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * cuando se pausa la aplicacion se guarda automaticamente
     */
    override fun onPause() {
        super.onPause()
        guardar()

    }

    /**
     * cuando se vuelve a la actividad recibe los datos compartidos por la otra actividad y setea el adaptador con la nueva informacion
     */
    override fun onResume() {
        super.onResume()

        getPreferencias()
        setAdaptador()
    }

    /**
     * cuando es llamado llama a la actividad de mostrar elemento
     * @property posicion posicion del elemento seleccionado del que sacaremos la informacion a pasar a la nueva actividad para que la muestre
     */
    override fun click(posicion: Int,codigo:Int) {
        when {
            codigo == Codigos.CLICK -> {
                val intent = Intent(this, VerElemento::class.java)
                putSharedPreference(
                    posicion,
                    elementos[posicion].contador,
                    elementos[posicion].nombre,
                    elementos[posicion].imagen
                )
                startActivity(intent)
            }
            codigo == Codigos.MAS -> { elementos[posicion].contador = (elementos[posicion].contador.toInt()+1).toString() }
            codigo == Codigos.MENOS -> { elementos[posicion].contador = (elementos[posicion].contador.toInt()-1).toString() }
            codigo == Codigos.BORRAR -> {
                elementos.removeAt(posicion)
                setAdaptador()
            }
        }
    }

    /**
     * inicializacion de los componentes que usaremos en el programa
     */
    private fun initComponents() {
        val serializador = Serializador(this)
        elementos = serializador.leer<Elemento>("elementos.json")

        recicler = findViewById(R.id.lista)
        val manager = LinearLayoutManager(this)
        recicler.layoutManager = manager


        setAdaptador()
    }

    /**
     * creacion de un nuevo elemento y adicion de este en la lista de elementos
     * @property nombre el nombre del elemento a crear
     * @property id de la imagen asociada al elemento
     */
    fun nuevoElemento(nombre:String,imagen:String){
        elementos.add(Elemento(nombre,imagen,"0"))
        Log.i("info","elementos $elementos")
        setAdaptador()
        guardar()
    }

    /**
     * seteamos el adaptador al RecyclerView para que lo muestre actualizado
     */
    private fun setAdaptador() {
        adaptador = Adaptador(elementos,this,this)
        recicler.adapter = adaptador
    }

    /**
     * guardamos la lista de elementos actualizada en el json
     */
    private fun guardar(){
        val serializador = Serializador(this)
        serializador.guardar<Elemento>(elementos,"elementos.json")
    }

    /**
     * pasamos a las shared preferences la informacion del elemento a mostrar en la actividad
     * @property posicion la posicion asociada a ese elemento
     * @property racha la racha acumulada por dicho elemento
     * @property nombre nombre del elemento
     * @param imagen id de la imagen asociada al elemento
     */
    private fun putSharedPreference(posicion:Int,racha:String,nombre:String,imagen: String){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        val editor = preferencias.edit()
        editor.putInt("posicion",posicion)
        editor.putString("racha",racha)
        editor.putString("nombre",nombre)
        editor.putString("verImagen",imagen)

        editor.apply()
    }

    /**
     * recive desde las SharedPreferences los nuevos datos dependiendo del booleano activo en estas, si esta activo
     * "segunda" sifnifica que la segunda actividad, aka la actividad de visualizacion, nos ha devuelto nueva informacion
     * "nuevo" significa que la actividad de creacion de nuevo elemento nos paso por parametro los datos del nuevo elemento
     *
     * en ambos casos se setea a false el booleano una vez se han recogido y usado apropiadamente, para asi evitar
     * que se usen en momentos inadecuados y cree conflictos no deseados
     */
    private fun getPreferencias(){
        val preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        /*Log.i("info","""nuevo ${preferencias.getBoolean("nuevo",false)}""")
        Log.i("info","""segunda ${preferencias.getBoolean("segunda", false)}""")*/

        when {
            preferencias.getBoolean("segunda", false)-> {

                elementos[preferencias.getInt("posicion", -1)].contador = preferencias.getString("racha","").toString()
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

    /**
     * infla el layout de que orden se quiere poner a la lista
     */
    private fun orderLayout(){
        val filtro = FilterType()
        filtro.show(supportFragmentManager,"filtro")
    }

    /**
     * llamada a orderList
     * @property boolean si se quiere ascendente o descendente, true ascendente, false descendente
     */
    fun order(boolean: Boolean){
        orderList(boolean)
    }

    /**
     * ordena la lista dependiendo del parametro y setea el adaptador con la lista ordenada
     * @property ascendente si se quiere ascendente o descendente, true ascendente, false descendente
     */
    private fun orderList(ascendente:Boolean){
        elementos = if(ascendente) {
            ListActions.ordenarLista(elementos,true)
        } else{
            ListActions.ordenarLista(elementos,false)
        }
        Log.i("info",elementos.toString())
        setAdaptador()
    }
}