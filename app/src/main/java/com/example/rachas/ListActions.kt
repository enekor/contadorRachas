package com.example.rachas

object ListActions {

    public fun orderAscendente(lista:ArrayList<Elemento>):ArrayList<Elemento>{
       lista.sortBy { v->v.contador }
        return lista
    }

    public fun orderDescendente(lista:ArrayList<Elemento>):ArrayList<Elemento>{
        lista.sortByDescending { v->v.contador }
        return lista
    }
}