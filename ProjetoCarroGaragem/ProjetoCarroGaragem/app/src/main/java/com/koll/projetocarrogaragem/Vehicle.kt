package com.koll.projetocarrogaragem

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val plate: String,
    val model: String,
    val color: String,
    val year: Int
)

