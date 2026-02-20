package com.unimag.sistemadevotacion.data

/**
 * El `enum class` Role define los diferentes cargos o roles a los que un candidato
 * se puede postular en el sistema de votación.
 *
 * El uso de un enum hace que el código sea más seguro y legible, evitando el uso de
 * strings literales que pueden llevar a errores.
 */
enum class Role {
    //Rol de contralor
    CONTRALOR,

    //Rol de personero
    PERSONERO
}
