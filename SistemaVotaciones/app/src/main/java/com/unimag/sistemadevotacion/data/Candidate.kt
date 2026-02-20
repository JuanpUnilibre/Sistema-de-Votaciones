package com.unimag.sistemadevotacion.data

/**
 * La `data class` Candidate representa a un candidato en el sistema de votación.
 *
 * @property id El identificador único del candidato.
 * @property name El nombre completo del candidato.
 * @property role El rol al que se postula el candidato (p. ej., CONTRALOR o PERSONERO).
 * @property imageRes El ID del recurso drawable para la imagen del candidato.
 */
data class Candidate(
    val id: Int,
    val name: String,
    val role: Role,
    val imageRes: Int
)
