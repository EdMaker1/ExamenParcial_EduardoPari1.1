package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import kotlin.collections.ArrayList

class GestorPuntuacion(contexto: Context) {

    private val preferencias: SharedPreferences = contexto.getSharedPreferences("puntos_guardados", Context.MODE_PRIVATE)

    // Guardar el puntaje máximo
    fun guardarPuntajeMaximo(puntaje: Int) {
        val mejoresPuntajes = obtenerMejoresPuntajes().toMutableList()
        if (mejoresPuntajes.size < 5) {
            mejoresPuntajes.add(puntaje)
        } else {
            // Reemplazar el puntaje más bajo si el nuevo puntaje es mayor
            mejoresPuntajes.sortDescending()
            if (puntaje > mejoresPuntajes.last()) {
                mejoresPuntajes[4] = puntaje
            }
        }
        mejoresPuntajes.sortDescending() // Ordenar de mayor a menor
        preferencias.edit().putStringSet("mejores_puntajes", mejoresPuntajes.map { it.toString() }.toSet()).apply()
    }

    // Obtener el puntaje máximo
    fun obtenerPuntajeMaximo(): Int {
        val mejoresPuntajes = obtenerMejoresPuntajes()
        return mejoresPuntajes.firstOrNull() ?: 0
    }

    // Obtener los mejores puntajes (top 5)
    fun obtenerMejoresPuntajes(): List<Int> {
        val puntajes = preferencias.getStringSet("mejores_puntajes", emptySet())?.map { it.toInt() } ?: emptyList()
        return puntajes.sortedDescending()
    }
}
