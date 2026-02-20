package com.unimag.sistemadevotacion.data

/**
 * El `object` VoteState es un singleton que mantiene el estado de la votación actual en memoria.
 * Se utiliza para almacenar temporalmente las selecciones del usuario mientras navega entre las
 * pantallas de votación de contralor y personero.
 */
object VoteState {
    /**
     * Constantes para identificar el voto en blanco. Usamos IDs que no entren en conflicto
     * con los IDs de los candidatos.
     */
    const val CONTRALOR_BLANCO_ID = 0
    const val PERSONERO_BLANCO_ID = -1 // Usamos -1 para diferenciarlo del de contralor.

    /**
     * Almacena el ID del candidato a contralor que ha sido seleccionado.
     * Puede ser un ID de candidato o CONTRALOR_BLANCO_ID.
     * Es `null` si no se ha seleccionado ninguno.
     */
    var selectedContralorId: Int? = null

    /**
     * Almacena el ID del candidato a personero que ha sido seleccionado.
     * Puede ser un ID de candidato o PERSONERO_BLANCO_ID.
     * Es `null` si no se ha seleccionado ninguno.
     */
    var selectedPersoneroId: Int? = null

    /**
     * La función `reset` limpia el estado de la votación actual, estableciendo
     * los IDs de los candidatos seleccionados a `null`.
     * Esto es útil para preparar el estado para un nuevo votante.
     */
    fun reset() {
        selectedContralorId = null
        selectedPersoneroId = null
    }
}
