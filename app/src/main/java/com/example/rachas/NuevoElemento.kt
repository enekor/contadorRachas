package com.example.rachas

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class NuevoElemento(main:MainActivity):DialogFragment() {
    val mainActivity = main
    lateinit var ok:Button
    lateinit var nombre:TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.nuevo,null)
        nombre = view?.findViewById(R.id.nombre) as TextView
        ok = view?.findViewById(R.id.ok) as Button
        builder.setView(view)

        ok.setOnClickListener(View.OnClickListener { view ->
            Log.i("info","boton pulsado")
            mainActivity.nuevoElemento(Elemento(nombre.text.toString(),-1,0))
            dismiss()
         })
        return builder.create()
    }
}