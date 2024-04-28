package com.example.appcompletasona.model

import java.io.Serializable

class Equipo(
    var nombreLiga: String,
    var idEquipo: String,
    var nombreEquipo: String,
    var isFav: Boolean = false): Serializable {
}