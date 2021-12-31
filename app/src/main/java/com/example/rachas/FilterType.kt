package com.example.rachas

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class FilterType:DialogFragment() {

    lateinit var textoAscendente: TextView
    lateinit var textoDescendente: TextView
    lateinit var iconoAscendente: ImageView
    lateinit var iconoDescendente: ImageView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = activity?.let { AlertDialog.Builder(it) }!!
        val inflater = activity!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.filtros,null)

        val main = activity as MainActivity?

        if (main != null) {
            initComponents(dialogView,main)
        }

        builder.setView(dialogView)

        return builder.create()
    }

    private fun initComponents(view: View, main: MainActivity) {
        textoAscendente = view.findViewById<TextView>(R.id.ascendente)
        textoDescendente = view.findViewById<TextView>(R.id.descendente)
        iconoAscendente = view.findViewById<ImageView>(R.id.ascendenteArrow)
        iconoDescendente = view.findViewById<ImageView>(R.id.descendenteArrow)

        textoAscendente.setOnClickListener{ orden(true,main) }
        iconoAscendente.setOnClickListener{ orden(true,main) }
        textoDescendente.setOnClickListener{ orden(false,main) }
        iconoDescendente.setOnClickListener{ orden(false,main) }
    }

    private fun orden(boolean:Boolean,main:MainActivity){
        if(boolean){
            main.order(true)
        }else{
            main.order(false)
        }
        dismiss()
    }
}