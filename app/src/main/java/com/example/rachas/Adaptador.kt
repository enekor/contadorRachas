package com.example.rachas

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

 class Adaptador(elementos:List<Elemento>,onCLick:OnClickElement): RecyclerView.Adapter<Adaptador.ViewHolder>() {
     val elementosList = elementos
     val elementClick = onCLick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.preview,parent,false)
        return ViewHolder(view,elementClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contador.text = elementosList.get(position).contador.toString()
        holder.nombre.text = elementosList.get(position).nombre
        holder.imagen.visibility = View.GONE
        holder.layout.setBackgroundColor(Color.BLUE)
    }

    override fun getItemCount(): Int {
        return elementosList.size
    }

    class ViewHolder(itemView: View,click:OnClickElement) : RecyclerView.ViewHolder(itemView) {
        val elementClick = click
        val imagen:ImageView
        val nombre:TextView
        val contador:TextView
        val layout:ConstraintLayout

        init {
            imagen = itemView.findViewById<ImageView>(R.id.imagenPreview)
            nombre = itemView.findViewById<TextView>(R.id.nombrePreview)
            contador = itemView.findViewById<TextView>(R.id.contadorPreview)
            layout = itemView.findViewById<ConstraintLayout>(R.id.layout)

            layout.setOnClickListener(View.OnClickListener { view ->
                Log.i("info","clicado")
                val position = adapterPosition
                elementClick.click(position)
            })
        }
    }
}