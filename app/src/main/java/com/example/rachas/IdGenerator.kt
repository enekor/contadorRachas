package com.example.rachas

import kotlin.random.Random

object IdGenerator {

    private const val MAX_LENGTH = 25
    private val caracteres = arrayOf(
        "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9","0"
    )
    var generatedIds = ArrayList<String>()

    /**
     * generador de Ids aleatorias de longitud $MAX_LENGTH
     * @return la id generada
     */
    public fun generarId():String{
        var returner = ""

        for (i in 0..MAX_LENGTH) {
            val random = Random.nextInt(0,caracteres.size)
            returner += caracteres[random]
        }

        return returner
    }
}