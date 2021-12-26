package com.example.rachas

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

 class Adaptador(elementos:List<Elemento>,onCLick:OnClickElement, context: Context): RecyclerView.Adapter<Adaptador.ViewHolder>() {
     val elementosList = elementos
     val elementClick = onCLick
     val contexto = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.preview,parent,false)
        return ViewHolder(view,elementClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contador.text = elementosList.get(position).contador.toString()
        holder.nombre.text = elementosList.get(position).nombre
        holder.imagen.setImageURI(ImageController.getImagen(contexto, elementosList[position].imagen))
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
        val borrar:ImageView

        init {
            imagen = itemView.findViewById<ImageView>(R.id.imagenPreview)
            nombre = itemView.findViewById<TextView>(R.id.nombrePreview)
            contador = itemView.findViewById<TextView>(R.id.contadorPreview)
            layout = itemView.findViewById<ConstraintLayout>(R.id.layout)
            borrar = itemView.findViewById<ImageView>(R.id.borrar)

            layout.setOnClickListener {
                elementClick.click(adapterPosition)
            }

            borrar.setOnClickListener{
                elementClick.onLongClick(adapterPosition)
            }
        }
    }
}