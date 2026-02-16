package com.unimag.sistemadevotacion.data

import android.content.Context

class VoteManager(private val ctx: Context) {
    private val prefs = ctx.getSharedPreferences("voting_prefs", Context.MODE_PRIVATE)

    // clave para total votantes completados
    private val KEY_TOTAL = "total_votantes"

    // prefijo para votos por candidato
    private fun keyForCandidate(id: Int) = "votes_$id"

    // incrementar voto para un candidato
    fun addVote(candidateId: Int) {
        val key = keyForCandidate(candidateId)
        val current = prefs.getInt(key, 0)
        prefs.edit().putInt(key, current + 1).apply()
    }

    // obtener votos de un candidato
    fun getVotes(candidateId: Int): Int {
        return prefs.getInt(keyForCandidate(candidateId), 0)
    }

    // incrementar total votantes (cada vez que un estudiante completa ambos votos)
    fun incrementTotalVotantes() {
        val current = prefs.getInt(KEY_TOTAL, 0)
        prefs.edit().putInt(KEY_TOTAL, current + 1).apply()
    }

    fun getTotalVotantes(): Int = prefs.getInt(KEY_TOTAL, 0)

    // reiniciar todo (admin)
    fun resetAll(candidateIds: List<Int>) {
        val editor = prefs.edit()
        for (id in candidateIds) editor.remove(keyForCandidate(id))
        editor.remove(KEY_TOTAL)
        editor.apply()
    }

    // marca hasVoted para esta tablet (si quieres bloquear votacion por tablet)
    fun setHasVoted(value: Boolean) {
        prefs.edit().putBoolean("has_voted", value).apply()
    }
    fun getHasVoted(): Boolean = prefs.getBoolean("has_voted", false)
}
