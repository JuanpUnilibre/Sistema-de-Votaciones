package com.unimag.sistemadevotacion.data

data class Candidate(
    val id: Int,
    val name: String,
    val role: String, // "contralor" o "personero"
    val imageRes: Int
)