package com.unimag.sistemadevotacion.data

import android.content.Context

/**
 * VoteManager es una clase que gestiona la lógica de la votación, incluyendo el almacenamiento
 * y la recuperación de los votos de forma persistente utilizando SharedPreferences.
 *
 * @param ctx El contexto de la aplicación, necesario para acceder a SharedPreferences.
 */
class VoteManager(private val ctx: Context) {
    // Se inicializa SharedPreferences para almacenar los datos de la votación.
    // "voting_prefs" es el nombre del fichero de preferencias.
    // Context.MODE_PRIVATE asegura que el fichero solo sea accesible por esta aplicación.
    private val prefs = ctx.getSharedPreferences("voting_prefs", Context.MODE_PRIVATE)

    // Clave para almacenar el número total de votantes que han completado el proceso.
    private val KEY_TOTAL = "total_votantes"

    /**
     * Genera una clave única para cada candidato basada en su ID.
     * Esto se usa para almacenar y recuperar el número de votos de cada uno.
     */
    private fun keyForCandidate(id: Int) = "votes_$id"

    /**
     * Incrementa en uno el contador de votos para un candidato específico.
     *
     * @param candidateId El ID del candidato que recibe el voto.
     */
    fun addVote(candidateId: Int) {
        val key = keyForCandidate(candidateId)
        val currentVotes = prefs.getInt(key, 0)
        prefs.edit().putInt(key, currentVotes + 1).apply()
    }

    /**
     * Obtiene el número de votos para un candidato específico.
     *
     * @param candidateId El ID del candidato.
     * @return El número de votos que ha recibido el candidato.
     */
    fun getVotes(candidateId: Int): Int {
        return prefs.getInt(keyForCandidate(candidateId), 0)
    }

    /**
     * Incrementa en uno el contador total de votantes que han finalizado el proceso.
     */
    fun incrementTotalVotantes() {
        val currentTotal = prefs.getInt(KEY_TOTAL, 0)
        prefs.edit().putInt(KEY_TOTAL, currentTotal + 1).apply()
    }

    /**
     * Obtiene el número total de votantes que han completado la votación.
     */
    fun getTotalVotantes(): Int = prefs.getInt(KEY_TOTAL, 0)

    /**
     * Reinicia todos los datos de la votación.
     * Elimina los votos de todos los candidatos y el contador total de votantes.
     * Esta función está pensada para ser usada por un administrador.
     *
     * @param candidateIds Una lista de los IDs de todos los candidatos para poder borrar sus datos.
     */
    fun resetAll(candidateIds: List<Int>) {
        val editor = prefs.edit()
        for (id in candidateIds) {
            editor.remove(keyForCandidate(id))
        }
        editor.remove(KEY_TOTAL)
        editor.apply()
    }

    /**
     * Marca si el dispositivo actual (tablet) ya ha sido utilizado para votar.
     * Esto puede usarse para prevenir que se vote varias veces desde el mismo dispositivo.
     *
     * @param value `true` si ya se ha votado, `false` en caso contrario.
     */
    fun setHasVoted(value: Boolean) {
        prefs.edit().putBoolean("has_voted", value).apply()
    }

   //compurueba si ya se ha votado en este dispositivo
    fun getHasVoted(): Boolean = prefs.getBoolean("has_voted", false)
}
