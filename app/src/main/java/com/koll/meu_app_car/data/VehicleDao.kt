package com.koll.meu_app_car.data

import androidx.room.*
import com.koll.meu_app_car.model.VehicleEntry

@Dao
interface VehicleDao {
    @Insert
    suspend fun insert(entry: VehicleEntry)

    @Query("SELECT * FROM vehicle_entries ORDER BY id DESC")
    suspend fun getAll(): List<VehicleEntry>

    @Update
    suspend fun update(entry: VehicleEntry)

    @Delete
    suspend fun delete(entry: VehicleEntry)

    // Métodos de busca
    @Query("SELECT * FROM vehicle_entries WHERE placaCarro LIKE '%' || :query || '%' ORDER BY id DESC")
    suspend fun searchByPlaca(query: String): List<VehicleEntry>

    @Query("SELECT * FROM vehicle_entries WHERE nomeCompleto LIKE '%' || :query || '%' ORDER BY id DESC")
    suspend fun searchByNome(query: String): List<VehicleEntry>

    @Query("SELECT * FROM vehicle_entries WHERE motivo LIKE '%' || :query || '%' ORDER BY id DESC")
    suspend fun searchByMotivo(query: String): List<VehicleEntry>

    @Query("""
        SELECT * FROM vehicle_entries 
        WHERE placaCarro LIKE '%' || :query || '%' 
        OR nomeCompleto LIKE '%' || :query || '%' 
        OR motivo LIKE '%' || :query || '%'
        OR modeloCor LIKE '%' || :query || '%'
        ORDER BY id DESC
    """)
    suspend fun searchAll(query: String): List<VehicleEntry>

    // Método para verificar se um veículo está registrado por primeiro nome e placa
    @Query("""
        SELECT * FROM vehicle_entries 
        WHERE nomeCompleto LIKE '%' || :firstName || '%' 
        AND placaCarro = :licensePlate
        LIMIT 1
    """)
    suspend fun checkVehicleByNameAndPlate(firstName: String, licensePlate: String): VehicleEntry?

    @Query("SELECT * FROM vehicle_entries WHERE placaCarro = :licensePlate LIMIT 1")
    suspend fun getVehicleByLicensePlate(licensePlate: String): VehicleEntry?

    // Método para autocompletar nomes
    @Query("SELECT * FROM vehicle_entries WHERE nomeCompleto LIKE :query || '%' ORDER BY nomeCompleto ASC")
    suspend fun getVehiclesByPartialName(query: String): List<VehicleEntry>

    // Método para excluir registros que começam com texto específico
    @Query("DELETE FROM vehicle_entries WHERE nomeCompleto LIKE :prefix || '%'")
    suspend fun deleteByNamePrefix(prefix: String)
}