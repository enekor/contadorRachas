package com.example.rachas

object ListActions {

    /**
     * ordena la lista de forma ascendente o descendente
     * @property lista la lista a ordenar
     * @property ascendente el orden a seguir, true si ascendete, false si descendente
     * @return la lista ordenada
     */
    fun ordenarLista(lista:ArrayList<Elemento>,ascendente:Boolean): ArrayList<Elemento> =
        if(ascendente) {ArrayList(lista.sortedBy { v->v.contador.toInt() }) } else {ArrayList(lista.sortedByDescending { v->v.contador.toInt() })}
}
