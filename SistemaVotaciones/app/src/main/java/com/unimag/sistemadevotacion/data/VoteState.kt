package com.unimag.sistemadevotacion.data

object VoteState {
    var selectedContralorId: Int? = null
    var selectedPersoneroId: Int? = null

    fun reset() {
        selectedContralorId = null
        selectedPersoneroId = null
    }
}
