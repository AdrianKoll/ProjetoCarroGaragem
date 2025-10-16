package com.projetocarrogaragem.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_entries")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomeCompleto: String,   // ✅ campo garantido
    val motivo: String,
    val placaCarro: String,
    val modeloCor: String,      // ✅ campo garantido
    val dataHora: String,       // ✅ substitui o antigo "data"
    val horarioEntrada: String,
    val horarioSaida: String? = null,
    val observacao: String? = null
)