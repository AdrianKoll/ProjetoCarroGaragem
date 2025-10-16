package com.koll.meu_app_car.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "vehicle_entries")
data class VehicleEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomeCompleto: String,
    val motivo: String,
    val placaCarro: String,
    val modeloCor: String,
    val data: String,
    val horarioEntrada: String,
    val horarioSaida: String?,
    val observacao: String?
)